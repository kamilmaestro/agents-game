package engine.decorator;

import engine.Message;
import engine.UserThread;
import engine.decorator.Messenger;

import java.util.List;

public abstract class MessengerDecorator implements Messenger {

  private Messenger messenger;

  public MessengerDecorator(Messenger messenger) {
    this.messenger = messenger;
  }

  @Override
  public void sendMessage(Message message) {
    messenger.sendMessage(message);
  }

  @Override
  public List<UserThread> getUserThreads() {
    return messenger.getUserThreads();
  }

}
