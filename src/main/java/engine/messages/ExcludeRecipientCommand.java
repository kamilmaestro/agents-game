package engine.messages;

import java.util.UUID;

class ExcludeRecipientCommand extends Command {

  private final String message;
  private final UUID specifiedPlayer;

  ExcludeRecipientCommand(Messenger messenger, String message, UUID specifiedPlayer) {
    super(messenger);
    this.message = message;
    this.specifiedPlayer = specifiedPlayer;
  }

  @Override
  boolean execute() {
    withBackup();
    messenger.setMessage(message);
    messenger.withUser(specifiedPlayer, true);
    messenger.sendMessage();
    return false;
  }

}
