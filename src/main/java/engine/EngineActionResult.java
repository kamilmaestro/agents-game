package engine;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class EngineActionResult {

  public final List<Message> messages;

  public EngineActionResult() {
    this.messages = new ArrayList<>();
  }

  public EngineActionResult addMessageForEveryone(String message) {
    messages.add(new Message(message));
    return this;
  }

  public EngineActionResult addMessageForSpecifiedPlayer(String message, UUID specifiedRecipient) {
    messages.add(new Message(message, specifiedRecipient, false));
    return this;
  }

}
