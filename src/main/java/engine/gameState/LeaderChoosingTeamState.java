package engine.gameState;

import engine.ActionsHandler;
import engine.EngineActionResult;
import game.Player;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class LeaderChoosingTeamState extends GameState {

  LeaderChoosingTeamState(ActionsHandler actionsHandler) {
    super(actionsHandler);
  }

  @Override
  public EngineActionResult vote(String message, UUID uuid) {
    return null;
  }

  @Override
  public EngineActionResult choose(String message, UUID uuid) {
    final Set<String> chosenPlayerNumbers = Set.of(message.split(","));
    final List<Player> players = actionsHandler.getGameFacade().getPlayers();
    final Set<UUID> chosenPlayersUuids = getPlayersToChoose(uuid, players)
        .filter(index -> chosenPlayerNumbers.contains(String.valueOf(index)))
        .mapToObj(index -> players.get(index).getUuid())
        .collect(Collectors.toSet());
    final StringBuilder stringBuilder = new StringBuilder("Selected team:");
    actionsHandler.getGameFacade().selectTeam(chosenPlayersUuids, uuid)
        .forEach(player -> stringBuilder.append(" ").append(player.getName()));
    stringBuilder.append(". Press t if you agree, otherwise press f.");
    actionsHandler.changeGameState(new AcceptingTeamState(actionsHandler));

    return new EngineActionResult().addMessageForEveryone(stringBuilder.toString());
  }

  private IntStream getPlayersToChoose(UUID leaderUuid, List<Player> players) {
    return IntStream.range(0, players.size())
        .filter(index -> !players.get(index).getUuid().equals(leaderUuid));
  }

}
