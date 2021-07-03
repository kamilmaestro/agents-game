package engine;

import java.util.Optional;
import java.util.UUID;

public final class Message {

  public final String message;
  private UUID specifiedRecipient;

  public Message(String message, UUID specifiedRecipient) {
    this.message = message;
    this.specifiedRecipient = specifiedRecipient;
  }

  public Message(String message) {
    this.message = message;
  }

  public Optional<UUID> getSpecifiedRecipient() {
    return Optional.ofNullable(specifiedRecipient);
  }

}
