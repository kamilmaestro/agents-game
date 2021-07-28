package engine.events;

import java.util.UUID;

import static engine.Server.getUserThreads;

public class UserJoinedListener implements UserEventsListener {

  @Override
  public void inform(UUID uuid, String name) {
    getUserThreads().stream()
        .filter(userThread -> userThread.uuid.equals(uuid))
        .findAny()
        .ifPresent(userThread -> userThread.sendMessage("Press t if you are ready"));
  }

}
