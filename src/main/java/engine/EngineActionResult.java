package engine;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

final class EngineActionResult {

  final List<Message> messages;

  EngineActionResult() {
    this.messages = new ArrayList<>();
  }

  EngineActionResult addMessageForEveryone(String message) {
    messages.add(new Message(message));
    return this;
  }

  EngineActionResult addMessageForSpecifiedPlayer(String message, UUID specifiedRecipient) {
    messages.add(new Message(message, specifiedRecipient));
    return this;
  }

}
