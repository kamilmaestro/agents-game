package engine.gameState;

import engine.ActionsHandler;
import engine.EngineActionResult;

import java.util.UUID;

class AgentsWinState extends GameState {

  public AgentsWinState(ActionsHandler actionsHandler) {
    super(actionsHandler);
  }

  @Override
  public EngineActionResult vote(String message, UUID uuid) {
    return null;
  }

  @Override
  public EngineActionResult choose(String message, UUID uuid) {
    return null;
  }

}
