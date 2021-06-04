package engine;

import java.util.Optional;
import java.util.UUID;

final class EngineActionResult {

  final String message;
  private UUID specifiedRecipient;

  EngineActionResult(String message, UUID specifiedRecipient) {
    this.message = message;
    this.specifiedRecipient = specifiedRecipient;
  }

  EngineActionResult(String message) {
    this.message = message;
  }

  public Optional<UUID> getSpecifiedRecipient() {
    return Optional.ofNullable(specifiedRecipient);
  }

}
