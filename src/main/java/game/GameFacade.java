package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class GameFacade {
  //starategy mam dla strategii w grze, metoda wytworcza mam dla Voting i VoteOperator
  private final Game game;
  private GameStrategy strategy;
  private final PlayerRepository playerRepository;
  //TODO chyba memento bylby dla obecnych glosow, bo moglbys je cofac itp, a state dla game?
  private final List<Vote> votes = new ArrayList<>();
  private final MissionsState missionsState;

  public GameFacade() {
    this.game = Game.createNew();
    this.playerRepository = new InMemoryPlayerRepository();
    this.missionsState = new MissionsState();
  }

  public Game markAsReady(UUID uuid) {
    playerRepository.findByUuid(uuid)
        .ifPresent(player -> game.markPlayerAsReady(player.getUuid()));
    return startIfPossible();
  }

  public UUID selectNewLeader() {
    return game.selectNewLeader();
  }

  public List<Player> getPlayers() {
    return game.getPlayers();
  }

  public List<UUID> getCurrentTeamUuids() {
    return game.getCurrentTeam().stream()
        .map(Player::getUuid)
        .collect(Collectors.toList());
  }

  public int getTeamSize() {
    return strategy.getTeamSize();
  }

  public List<Player> selectTeam(Set<UUID> chosenPlayers, UUID uuid) {
    chosenPlayers.add(uuid);
    return game.selectTeam(chosenPlayers, strategy);
  }

  public VoteResult acceptTeam(boolean accepts, UUID uuid) {
    return vote(accepts, VoteOperator.VoteMode.ACCEPTING_TEAM, uuid);
  }

  public VoteResult voteForMissionSuccess(boolean success, UUID uuid) {
    final VoteResult voteResult = vote(success, VoteOperator.VoteMode.MISSION, uuid);
    return missionsState.updateMission(voteResult);
  }

  private VoteResult vote(boolean success, VoteOperator.VoteMode voteMode, UUID uuid) {
    final VoteOperator voteOperator = VoteOperator.VoteMode.ACCEPTING_TEAM.equals(voteMode) ?
        new AcceptingTeamVoteOperator(game.getPlayers(), votes)
        : new MissionVoteOperator(game.getCurrentTeam(), votes);
    final VoteResult voteResult = voteOperator.vote(success, uuid);
    if (voteResult.isCompleted()) {
      votes.clear();
    }

    return voteResult;
  }

  public void onUserCreated(UUID uuid, String name) {
    if (!game.hasStarted()) {
      final Player player = playerRepository.save(new Player(uuid, name));
      game.addPlayer(player);
    }
  }

  Game getGame() {
    return game;
  }

  private GameStrategy createStrategy() {
    switch (game.getPlayers().size()) {
      case 3:
      case 4:
        return new ThreePlayersStrategy();
      case 5:
      case 6:
        return new FivePlayersStrategy();
      case 7:
      case 8:
        return new SevenPlayersStrategy();
      case 9:
      case 10:
        return new NinePlayersStrategy();
      default:
        throw new RuntimeException();
    }
  }

  private Game startIfPossible() {
    if (canStart()) {
      strategy = createStrategy();
      return game.start(strategy);
    } else {
      return game;
    }
  }

  private boolean canStart() {
    final List<Player> players = game.getPlayers();
    final long readyPlayers = players.stream()
        .filter(Player::isReady)
        .count();
    return players.size() >= 3 && players.size() == readyPlayers;
  }

}
