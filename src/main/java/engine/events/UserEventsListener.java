package engine.events;

import java.util.UUID;

public interface UserEventsListener {

  void inform(UUID uuid, String name);

}
