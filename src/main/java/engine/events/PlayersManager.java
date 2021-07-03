package engine.events;

import game.GameFacade;

import java.util.UUID;

public class PlayersManager {

  private final UserEventsManager userEventsManager;
  private final GameFacade gameFacade;

  public PlayersManager(GameFacade gameFacade) {
    this.userEventsManager = new UserEventsManager("created", "removed");
    this.gameFacade = gameFacade;
  }

  public void addUser(UUID uuid, String name) {
    gameFacade.onUserCreated(uuid, name);
    userEventsManager.inform("created", uuid, name);
  }

  public void removeUser(UUID uuid, String name) {
    userEventsManager.inform("removed", uuid, name);
  }

}
