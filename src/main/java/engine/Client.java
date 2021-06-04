package engine;

import java.net.*;
import java.io.*;

public class Client {
  private final String hostname;
  private final int port;

  public Client(String hostname, int port) {
    this.hostname = hostname;
    this.port = port;
  }

  public static void main(String[] args) {
    Client client = new Client("localhost", Server.PORT);
    client.execute();
  }

  private void execute() {
    try {
      Socket socket = new Socket(hostname, port);
      System.out.println("Connected to the game");
      new ReadThread(socket).start();
      new WriteThread(socket, this).start();
    } catch (UnknownHostException ex) {
      System.out.println("Server not found: " + ex.getMessage());
    } catch (IOException ex) {
      System.out.println("I/O Error: " + ex.getMessage());
    }
  }

}
