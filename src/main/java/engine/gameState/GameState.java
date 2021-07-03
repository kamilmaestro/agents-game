package engine.gameState;

import engine.ActionsHandler;
import engine.EngineActionResult;

import java.util.UUID;

public abstract class GameState {

  ActionsHandler actionsHandler;

  public GameState(ActionsHandler actionsHandler) {
    this.actionsHandler = actionsHandler;
  }

  public abstract EngineActionResult vote(String message, UUID uuid);
  public abstract EngineActionResult choose(String message, UUID uuid);

}
