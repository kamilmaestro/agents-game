package engine;

import game.Game;
import game.GameFacade;
import game.Player;
import game.VoteResult;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

final class Engine {

  private final GameFacade gameFacade;
  private Server server;
  private ActionType currentAction; //TODO tu chyba idealnie spasuje state :P

  Engine() {
    this.currentAction = ActionType.PLAYERS_MARKING_AS_READY;
    this.gameFacade = new GameFacade();
  }

  void setServer(Server server) {
    this.server = server;
  }

  EngineActionResult newUser(UUID uuid, String name) {
    gameFacade.onUserCreated(uuid, name);
    return new EngineActionResult().addMessageForSpecifiedPlayer("Press 1 if you are ready", uuid);
  }

  void userLeft(UUID uuid) {

  }

  EngineActionResult handle(String message, UUID uuid) {
    if (currentAction.equals(ActionType.PLAYERS_MARKING_AS_READY)) {
      return markAsReady(message, uuid);
    } else if (currentAction.equals(ActionType.LEADER_CHOOSING_TEAM)) {
      return selectTeam(message, uuid);
    } else if (currentAction.equals(ActionType.ACCEPTING_TEAM)) {
      return acceptTeam(message, uuid);
    }
    return null;
  }

  private EngineActionResult acceptTeam(String message, UUID uuid) {
    if (!message.equals("1") && !message.equals("2")) {
      return null;
    }
    final VoteResult voteResult = gameFacade.acceptTeam(message.equals("1"), uuid);
    final StringBuilder stringBuilder = new StringBuilder();
    if (VoteResult.APPROVED.equals(voteResult)) {
      stringBuilder.append("Team was accepted. Prepare for the mission.");
      currentAction = ActionType.VOTING_FOR_MISSION_SUCCESS;
    } else if (VoteResult.IN_PROGRESS.equals(voteResult)) {
      stringBuilder.append("You vote was count. Wait for the others.");
      return new EngineActionResult().addMessageForSpecifiedPlayer(stringBuilder.toString(), uuid);
    } else {
      stringBuilder.append("Team was not accepted. Prepare for the next creation of the team.");
      currentAction = ActionType.LEADER_CHOOSING_TEAM;
      final UUID leaderUuid = gameFacade.selectNewLeader();
      return new EngineActionResult()
          .addMessageForEveryone(stringBuilder.toString())
          .addMessageForSpecifiedPlayer(getInfoForLeader(leaderUuid), leaderUuid);
    }

    return null;
  }

  private EngineActionResult selectTeam(String message, UUID uuid) {
    final Set<String> chosenPlayerNumbers = Set.of(message.split(","));
    final List<Player> players = gameFacade.getPlayers();
    final Set<UUID> chosenPlayersUuids = getPlayersToChoose(uuid, players)
        .filter(index -> chosenPlayerNumbers.contains(String.valueOf(index)))
        .mapToObj(index -> players.get(index).getUuid())
        .collect(Collectors.toSet());
    final StringBuilder stringBuilder = new StringBuilder("Selected team:");
    gameFacade.selectTeam(chosenPlayersUuids, uuid)
        .forEach(player -> stringBuilder.append(" ").append(player.getName()));
    stringBuilder.append(". Press 1 if you agree, otherwise press 2");
    currentAction = ActionType.ACCEPTING_TEAM;

    return new EngineActionResult().addMessageForEveryone(stringBuilder.toString());
  }

  private EngineActionResult markAsReady(String message, UUID uuid) {
    if (!message.equals("1")) {
      return null;
    }
    final Game game = gameFacade.markAsReady(uuid);
    if (game.hasStarted()) {
      currentAction = ActionType.LEADER_CHOOSING_TEAM;
      final UUID leaderUuid = gameFacade.selectNewLeader();
      return new EngineActionResult()
          .addMessageForEveryone("Game started. Have fun :D")
          .addMessageForSpecifiedPlayer(getInfoForLeader(leaderUuid), leaderUuid);
    } else {
      return new EngineActionResult().addMessageForSpecifiedPlayer("You are ready! Wait for the others.", uuid);
    }
  }

  private String getInfoForLeader(UUID leaderUuid) {
    final int toChoose = gameFacade.getTeamSize() - 1;
    final StringBuilder stringBuilder = new StringBuilder("You are a leader. Write numbers after comma to choose ")
        .append(toChoose)
        .append(" players (you are part of the team by definition):\n");
    final List<Player> players = gameFacade.getPlayers();
    getPlayersToChoose(leaderUuid, players)
        .forEach(index -> stringBuilder.append(index + 1).append(". ").append(players.get(index).getName()).append("  "));

    return stringBuilder.toString();
  }

  private IntStream getPlayersToChoose(UUID leaderUuid, List<Player> players) {
    return IntStream.range(0, players.size())
        .filter(index -> !players.get(index).getUuid().equals(leaderUuid));
  }

}
