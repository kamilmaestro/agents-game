package engine;

import java.util.UUID;

final class Engine {

  private ActionType currentAction;

  Engine() {
    this.currentAction = ActionType.PLAYERS_MARKING_AS_READY;
  }

  EngineActionResult newUser(UUID uuid, String name) {
    return new EngineActionResult("Press 1 if you are ready", uuid);
  }

  void userLeft(UUID uuid) {

  }

  void handle(String message) {

  }

}
