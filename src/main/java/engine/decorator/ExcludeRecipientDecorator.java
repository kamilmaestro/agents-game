package engine.decorator;

import engine.Message;
import engine.UserThread;

import java.util.List;
import java.util.UUID;

public final class ExcludeRecipientDecorator extends MessengerDecorator {

  public ExcludeRecipientDecorator(Messenger messenger) {
    super(messenger);
  }

  @Override
  public void sendMessage(Message message) {
    super.sendMessage(message);
    if (message.getSpecifiedRecipient().isPresent()) {
      sendToEveryoneExcept(message.getSpecifiedRecipient().get(), message.message);
    }
  }

  @Override
  public List<UserThread> getUserThreads() {
    return super.getUserThreads();
  }

  private void sendToEveryoneExcept(UUID userUuid, String message) {
    for (UserThread thread : getUserThreads()) {
      if (!thread.uuid.equals(userUuid)) {
        thread.sendMessage(message);
      }
    }
  }

}
