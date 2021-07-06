package engine.messages;

import engine.UserThread;

import java.util.List;
import java.util.UUID;

public class MessengerInvoker {

  private final Messenger messenger;
  private final CommandHistory commandHistory;

  public MessengerInvoker() {
    this.messenger = new Messenger();
    this.commandHistory = new CommandHistory();
  }

  public enum MessageType {
    BROADCAST,
    EXCLUDED_RECIPIENT,
    SPECIFIED_RECIPIENT
  }

  public void sendMessage(String message, UUID specifiedPlayer, MessageType type) {
    switch (type) {
      case BROADCAST:
        executeCommand(new BroadcastCommand(this.messenger, message));
        break;
      case EXCLUDED_RECIPIENT:
        executeCommand(new ExcludeRecipientCommand(this.messenger, message, specifiedPlayer));
        break;
      case SPECIFIED_RECIPIENT:
        executeCommand(new SpecifiedRecipientCommand(this.messenger, message, specifiedPlayer));
        break;
      default:
        break;
    }
  }

  private void executeCommand(Command command) {
    if (command.execute()) {
      commandHistory.push(command);
    }
  }

  private void undo() {
    if (commandHistory.isEmpty()) {
      return;
    }

    Command command = commandHistory.pop();
    if (command != null) {
      command.undo();
      command.messenger.sendMessage();
    }
  }

}
