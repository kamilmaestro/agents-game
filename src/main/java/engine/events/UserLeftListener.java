package engine.events;

import engine.Server;
import engine.UserThread;

import java.util.HashMap;
import java.util.UUID;

import static engine.Server.getUserThreads;

public class UserLeftListener implements UserEventsListener {

  @Override
  public void inform(UUID uuid, String name) {
    for (UserThread thread : getUserThreads()) {
      thread.sendMessage("User has left the game. Name: " + name + ", UUID: " + uuid);
    }
  }

}
