package engine;

import engine.gameState.GameState;
import engine.gameState.MarkingAsReadyState;
import game.GameFacade;

import java.util.UUID;

public class ActionsHandler {

  private GameState gameState;
  private final GameFacade gameFacade;

  public ActionsHandler(GameFacade gameFacade) {
    this.gameFacade = gameFacade;
    this.gameState = new MarkingAsReadyState(this);
  }

  EngineActionResult vote(String message, UUID uuid) {
    return gameState.vote(message, uuid);
  }

  EngineActionResult choose(String message, UUID uuid) {
    return gameState.choose(message, uuid);
  }

  public GameFacade getGameFacade() {
    return gameFacade;
  }

  public void changeGameState(GameState gameState) {
    this.gameState = gameState;
  }

}
