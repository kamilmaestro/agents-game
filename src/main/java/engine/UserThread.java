package engine;

import java.io.*;
import java.net.*;
import java.util.*;

public class UserThread extends Thread {
  private Socket socket;
  private Server server;
  private PrintWriter writer;
  public final UUID uuid;

  public UserThread(Socket socket, Server server) {
    this.socket = socket;
    this.server = server;
    this.uuid = UUID.randomUUID();
  }

  public void run() {
    try {
      final InputStream input = socket.getInputStream();
      final BufferedReader reader = new BufferedReader(new InputStreamReader(input));
      final OutputStream output = socket.getOutputStream();
      writer = new PrintWriter(output, true);

      printUsers();
      final String userName = reader.readLine();
      server.addUserName(uuid, userName);
      String serverMessage = "New user joined a game: " + userName;
      server.broadcastWithout(serverMessage, uuid);

      String clientMessage;
      do {
        clientMessage = reader.readLine();
        server.handle(clientMessage, uuid);

      } while (!clientMessage.equals("Exit"));

      server.removeUser(uuid);
      socket.close();
      serverMessage = userName + " has quitted.";
      server.broadcast(serverMessage);
    } catch (IOException ex) {
      System.out.println("Error in UserThread: " + ex.getMessage());
      ex.printStackTrace();
    }
  }

  private void printUsers() {
    if (server.hasUsers()) {
      writer.println("Connected users: " + server.getUserNames());
    } else {
      writer.println("No other users connected");
    }
  }

  public void sendMessage(String message) {
    writer.println(message);
  }
}

