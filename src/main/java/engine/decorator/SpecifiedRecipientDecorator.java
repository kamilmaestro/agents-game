package engine.decorator;

import engine.Message;
import engine.UserThread;

import java.util.List;
import java.util.UUID;

public final class SpecifiedRecipientDecorator extends MessengerDecorator {

  public SpecifiedRecipientDecorator(Messenger messenger) {
    super(messenger);
  }

  @Override
  public void sendMessage(Message message) {
    super.sendMessage(message);
    if (message.getSpecifiedRecipient().isPresent()) {
      sendTo(message.getSpecifiedRecipient().get(), message.message);
    }
  }

  @Override
  public List<UserThread> getUserThreads() {
    return super.getUserThreads();
  }

  private void sendTo(UUID userUuid, String message) {
    getUserThreads().stream()
        .filter(userThread -> userThread.uuid.equals(userUuid))
        .findAny()
        .ifPresent(userThread -> userThread.sendMessage(message));
  }

}
