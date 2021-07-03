package engine.gameState;

import engine.ActionsHandler;
import engine.EngineActionResult;
import game.Game;
import game.Player;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

public class MarkingAsReadyState extends GameState {

  public MarkingAsReadyState(ActionsHandler actionsHandler) {
    super(actionsHandler);
  }

  @Override
  public EngineActionResult vote(String message, UUID uuid) {
    if (!message.equals("t")) {
      return null;
    }
    final Game game = actionsHandler.getGameFacade().markAsReady(uuid);
    if (game.hasStarted()) {
      actionsHandler.changeGameState(new LeaderChoosingTeamState(actionsHandler));
      final UUID leaderUuid = actionsHandler.getGameFacade().selectNewLeader();
      return new EngineActionResult()
          .addMessageForEveryone("Game started. Have fun :D\n")
          .addMessageForSpecifiedPlayer(getInfoForLeader(leaderUuid), leaderUuid);
    } else {
      return new EngineActionResult().addMessageForSpecifiedPlayer("You are ready! Wait for the others.\n", uuid);
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
