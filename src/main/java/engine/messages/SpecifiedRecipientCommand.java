package engine.messages;

import java.util.UUID;

class SpecifiedRecipientCommand extends Command {

  private final String message;
  private final UUID specifiedPlayer;

  SpecifiedRecipientCommand(Messenger messenger, String message, UUID specifiedPlayer) {
    super(messenger);
    this.message = message;
    this.specifiedPlayer = specifiedPlayer;
  }

  @Override
  boolean execute() {
    withBackup();
    messenger.setMessage(message);
    messenger.withUser(specifiedPlayer, false);
    messenger.sendMessage();
    return false;
  }

}
