package engine.decorator;

import engine.Message;
import engine.UserThread;

import java.util.List;

public interface Messenger {

  void sendMessage(Message message);

  List<UserThread> getUserThreads();

}
