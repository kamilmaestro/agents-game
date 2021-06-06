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

import static engine.ActionType.*;

final class Engine {

  private final GameFacade gameFacade;
  private ActionType currentAction; //TODO tu chyba idealnie spasuje state :P

  Engine() {
    this.currentAction = PLAYERS_MARKING_AS_READY;
    this.gameFacade = new GameFacade();
  }

  EngineActionResult newUser(UUID uuid, String name) {
    gameFacade.onUserCreated(uuid, name);
    return new EngineActionResult().addMessageForSpecifiedPlayer("Press 1 if you are ready", uuid);
  }

  void userLeft(UUID uuid) {

  }

  EngineActionResult handle(String message, UUID uuid) {
    if (PLAYERS_MARKING_AS_READY.equals(currentAction)) {
      return markAsReady(message, uuid);
    } else if (LEADER_CHOOSING_TEAM.equals(currentAction)) {
      return selectTeam(message, uuid);
    } else if (ACCEPTING_TEAM.equals(currentAction)) {
      return acceptTeam(message, uuid);
    } else if (VOTING_FOR_MISSION_SUCCESS.equals(currentAction)) {
      return voteForMissionSuccess(message, uuid);
    } else if (AGENTS_WIN.equals(currentAction)) {

    } else if (SPIES_WIN.equals(currentAction)) {

    }
    return null;
  }

  private EngineActionResult voteForMissionSuccess(String message, UUID uuid) {
    if (!message.equals("1") && !message.equals("2")) {
      return null;
    }
    final VoteResult voteResult = gameFacade.voteForMissionSuccess(message.equals("1"), uuid);
    final String info;
    if (VoteResult.APPROVED.equals(voteResult)) {
      currentAction = LEADER_CHOOSING_TEAM;
      info = "Mission was successful!\n";
      final String infoForCurrentLeader = "Press 1 if you find that mission was successful, otherwise press 2.\n";
      final UUID leaderUuid = gameFacade.selectNewLeader();

      return new EngineActionResult()
          .addMessageForEveryone(info)
          .addMessageForSpecifiedPlayer(infoForCurrentLeader, leaderUuid);
    } else if (VoteResult.IN_PROGRESS.equals(voteResult)) {
      info = "You vote was count. Wait for the others.\n";
      return new EngineActionResult().addMessageForSpecifiedPlayer(info, uuid);
    } else if (AGENTS_WIN.equals(currentAction)) {
      
    } else if (SPIES_WIN.equals(currentAction)) {

    } else {
      currentAction = LEADER_CHOOSING_TEAM;
      info = "Mission failure :(\n";
      final UUID leaderUuid = gameFacade.selectNewLeader();

      return new EngineActionResult()
          .addMessageForEveryone(info)
          .addMessageForSpecifiedPlayer(getInfoForLeader(leaderUuid), leaderUuid);
    }
    return null;
  }

  private EngineActionResult acceptTeam(String message, UUID uuid) {
    if (!message.equals("1") && !message.equals("2")) {
      return null;
    }
    final VoteResult voteResult = gameFacade.acceptTeam(message.equals("1"), uuid);
    final String info;
    if (VoteResult.APPROVED.equals(voteResult)) {
      currentAction = VOTING_FOR_MISSION_SUCCESS;
      info = "Team was accepted. Mission is in progress...\n";
      final EngineActionResult actionResult = new EngineActionResult().addMessageForEveryone(info);
      final String infoForCurrentTeam = "Press 1 if you find that mission was successful, otherwise press 2.\n";
      gameFacade.getCurrentTeamUuids()
          .forEach(playerUuid -> actionResult.addMessageForSpecifiedPlayer(infoForCurrentTeam, playerUuid));

      return actionResult;
    } else if (VoteResult.IN_PROGRESS.equals(voteResult)) {
      info = "You vote was count. Wait for the others.\n";
      return new EngineActionResult().addMessageForSpecifiedPlayer(info, uuid);
    } else {
      currentAction = LEADER_CHOOSING_TEAM;
      info = "Team was not accepted. Prepare for the next creation of the team.\n";
      final UUID leaderUuid = gameFacade.selectNewLeader();

      return new EngineActionResult()
          .addMessageForEveryone(info)
          .addMessageForSpecifiedPlayer(getInfoForLeader(leaderUuid), leaderUuid);
    }
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
    stringBuilder.append(". Press 1 if you agree, otherwise press 2.");
    currentAction = ACCEPTING_TEAM;

    return new EngineActionResult().addMessageForEveryone(stringBuilder.toString());
  }

  private EngineActionResult markAsReady(String message, UUID uuid) {
    if (!message.equals("1")) {
      return null;
    }
    final Game game = gameFacade.markAsReady(uuid);
    if (game.hasStarted()) {
      currentAction = LEADER_CHOOSING_TEAM;
      final UUID leaderUuid = gameFacade.selectNewLeader();
      return new EngineActionResult()
          .addMessageForEveryone("Game started. Have fun :D\n")
          .addMessageForSpecifiedPlayer(getInfoForLeader(leaderUuid), leaderUuid);
    } else {
      return new EngineActionResult().addMessageForSpecifiedPlayer("You are ready! Wait for the others.\n", uuid);
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
