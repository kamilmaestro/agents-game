package engine.messages;

import java.util.UUID;

class BroadcastCommand extends Command {

  private final String message;

  BroadcastCommand(Messenger messenger, String message) {
    super(messenger);
    this.message = message;
  }

  @Override
  boolean execute() {
    withBackup();
    messenger.setMessage(message);
    messenger.withoutRecipient();
    messenger.sendMessage();
    return true;
  }

}
