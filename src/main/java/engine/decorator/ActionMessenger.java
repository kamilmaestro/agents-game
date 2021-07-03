package engine.decorator;

import engine.Message;
import engine.UserThread;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class ActionMessenger implements Messenger {

  final List<Message> messages;
  final List<UserThread> userThreads;

  public ActionMessenger(List<UserThread> userThreads) {
    this.userThreads = userThreads;
    this.messages = new ArrayList<>();
  }

  public ActionMessenger addMessageForEveryone(String message) {
    messages.add(new Message(message));
    return this;
  }

  public ActionMessenger addMessageForSpecifiedPlayer(String message, UUID specifiedRecipient) {
    messages.add(new Message(message, specifiedRecipient));
    return this;
  }

  @Override
  public void sendMessage(Message message) {
    for (UserThread thread : userThreads) {
      thread.sendMessage(message.message);
    }
  }

  @Override
  public List<UserThread> getUserThreads() {
    return userThreads;
  }

}

