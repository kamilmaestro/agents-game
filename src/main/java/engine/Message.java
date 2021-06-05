package engine;

import java.util.Optional;
import java.util.UUID;

final class Message {

  final String message;
  private UUID specifiedRecipient;

  Message(String message, UUID specifiedRecipient) {
    this.message = message;
    this.specifiedRecipient = specifiedRecipient;
  }

  Message(String message) {
    this.message = message;
  }

  public Optional<UUID> getSpecifiedRecipient() {
    return Optional.ofNullable(specifiedRecipient);
  }

}
