package engine;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.UUID;

public class Server {

  private final HashMap<UUID, UserThread> userThreads = new HashMap<>();
  private final HashMap<UUID, String> userNames = new HashMap<>();
  private final Engine engine;
  public static final int PORT = 2345;

  Server() {
    engine = new Engine();
  }

  public static void main(String[] args) {
    new Server();
  }

  private void start() {
    try (ServerSocket serverSocket = new ServerSocket(PORT)) {
      System.out.println("Chat Server is listening on port " + PORT);
      while (true) {
        Socket socket = serverSocket.accept();
        System.out.println("New user connected");
        final UserThread userThread = new UserThread(socket, this);
        userThreads.put(userThread.uuid, userThread);
        userThread.start();
      }
    } catch (IOException ex) {
      System.out.println("Error in the server: " + ex.getMessage());
      ex.printStackTrace();
    }
  }

  void handle(String message, UUID uuid) {
    final EngineActionResult actionResult = engine.handle(message, uuid);
    if (actionResult != null) {
      actionResult.messages
          .forEach(messageToSend ->
              messageToSend.getSpecifiedRecipient().ifPresentOrElse(
                  recipientUuid -> sendMessageTo(recipientUuid, messageToSend.message),
                  () -> broadcast(messageToSend.message)
              )
          );
    }
  }

  void broadcastWithout(String message, UUID uuid) {
    for (UserThread thread : userThreads.values()) {
      if (!thread.uuid.equals(uuid)) {
        thread.sendMessage(message);
      }
    }
  }

  void broadcast(String message) {
    for (UserThread thread : userThreads.values()) {
      thread.sendMessage(message);
    }
  }

  void sendMessageTo(UUID uuid, String message) {
    userThreads.entrySet().stream()
        .filter(entry -> entry.getKey().equals(uuid))
        .findAny()
        .ifPresent(entry -> entry.getValue().sendMessage(message));
  }

  void removeUser(UUID uuid) {
    userThreads.remove(uuid);
    userNames.remove(uuid);
    engine.userLeft(uuid);
  }

  boolean hasUsers() {
    return !userNames.values().isEmpty();
  }

  void addUserName(UUID uuid, String userName) {
    userNames.put(uuid, userName);
    final EngineActionResult result = engine.newUser(uuid, userName);
    sendMessageTo(uuid, result.messages.get(0).message);
  }

  String getUserNames() {
    return String.join(", ", userNames.values());
  }

}
