package engine;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class Server {

  private final HashMap<UUID, UserThread> userThreads = new HashMap<>();
  private final HashMap<UUID, String> userNames = new HashMap<>();
  private final Engine engine;
  public static final int PORT = 2345;

  public Server() {
    engine = new Engine();
  }

  public static void main(String[] args) {
    new Server().start();
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

  void handle(String message) {
    engine.handle(message);
  }

  void broadcastWithout(String message, UUID uuid) {
    for (UserThread thread : userThreads.values()) {
      if (thread.uuid.equals(uuid)) {
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
    sendMessageTo(uuid, result.message);
  }

  String getUserNames() {
    return String.join(", ", userNames.values());
  }

}
