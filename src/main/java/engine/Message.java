package engine;

import java.util.Optional;
import java.util.UUID;

public final class Message {

  public final String message;
  private UUID specifiedRecipient;
  private boolean isExcluded = false;

  public Message(String message, UUID specifiedRecipient, boolean isExcluded) {
    this.message = message;
    this.specifiedRecipient = specifiedRecipient;
    this.isExcluded = isExcluded;
  }

  public Message(String message) {
    this.message = message;
  }

  public Optional<UUID> getSpecifiedRecipient() {
    return Optional.ofNullable(specifiedRecipient);
  }

}
