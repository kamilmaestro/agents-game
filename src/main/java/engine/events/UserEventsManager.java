package engine.events;

import java.util.*;

public class UserEventsManager {

  Map<String, List<UserEventsListener>> listeners = new HashMap<>();

  public UserEventsManager(String... operations) {
    for (String operation : operations) {
      this.listeners.put(operation, new ArrayList<>());
    }
  }

  public void subscribe(String eventType, UserEventsListener listener) {
    List<UserEventsListener> listeners = this.listeners.get(eventType);
    listeners.add(listener);
  }

  public void unsubscribe(String eventType, UserEventsListener listener) {
    List<UserEventsListener> listeners = this.listeners.get(eventType);
    listeners.remove(listener);
  }

  public void inform(String eventType, UUID uuid, String name) {
    List<UserEventsListener> users = listeners.get(eventType);
    for (UserEventsListener listener : users) {
      listener.inform(uuid, name);
    }
  }

}
