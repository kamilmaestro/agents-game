package engine.gameState;

import engine.ActionsHandler;
import engine.EngineActionResult;
import game.Player;
import game.voting.VoteResult;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

class AcceptingTeamState extends GameState {

  AcceptingTeamState(ActionsHandler actionsHandler) {
    super(actionsHandler);
  }

  @Override
  public EngineActionResult vote(String message, UUID uuid) {
    if (!message.equals("t") && !message.equals("f")) {
      return null;
    }
    final VoteResult voteResult = actionsHandler.getGameFacade().acceptTeam(message.equals("t"), uuid);
    final String info;
    if (VoteResult.APPROVED.equals(voteResult)) {
      actionsHandler.changeGameState(new VotingForMissionSuccess(actionsHandler));
      info = "Team was accepted. Mission is in progress...\n";
      final EngineActionResult actionResult = new EngineActionResult().addMessageForEveryone(info);
      final String infoForCurrentTeam = "Press t if you find that mission was successful, otherwise press f.\n";
      actionsHandler.getGameFacade().getCurrentTeamUuids()
          .forEach(playerUuid -> actionResult.addMessageForSpecifiedPlayer(infoForCurrentTeam, playerUuid));

      return actionResult;
    } else if (VoteResult.IN_PROGRESS.equals(voteResult)) {
      info = "You vote was count. Wait for the others.\n";
      return new EngineActionResult().addMessageForSpecifiedPlayer(info, uuid);
    } else {
      actionsHandler.changeGameState(new LeaderChoosingTeamState(actionsHandler));
      info = "Team was not accepted. Prepare for the next creation of the team.\n";
      final UUID leaderUuid = actionsHandler.getGameFacade().selectNewLeader();

      return new EngineActionResult()
          .addMessageForEveryone(info)
          .addMessageForSpecifiedPlayer(getInfoForLeader(leaderUuid), leaderUuid);
    }
  }

  @Override
  public EngineActionResult choose(String message, UUID uuid) {
    return null;
  }

  private String getInfoForLeader(UUID leaderUuid) {
    final int toChoose = actionsHandler.getGameFacade().getTeamSize() - 1;
    final StringBuilder stringBuilder = new StringBuilder("You are a leader. Write numbers after comma to choose ")
        .append(toChoose)
        .append(" players (you are part of the team by definition):\n");
    final List<Player> players = actionsHandler.getGameFacade().getPlayers();
    getPlayersToChoose(leaderUuid, players)
        .forEach(index -> stringBuilder.append(index + 1).append(". ").append(players.get(index).getName()).append("  "));

    return stringBuilder.toString();
  }

  private IntStream getPlayersToChoose(UUID leaderUuid, List<Player> players) {
    return IntStream.range(0, players.size())
        .filter(index -> !players.get(index).getUuid().equals(leaderUuid));
  }

}
