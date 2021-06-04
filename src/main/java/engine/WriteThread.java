package engine;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class WriteThread extends Thread {

  private PrintWriter writer;
  private final Socket socket;
  private final Client client;

  public WriteThread(Socket socket, Client client) {
    this.socket = socket;
    this.client = client;
    try {
      OutputStream output = socket.getOutputStream();
      writer = new PrintWriter(output, true);
    } catch (IOException ex) {
      System.out.println("Error getting output stream: " + ex.getMessage());
      ex.printStackTrace();
    }
  }

  public void run() {
    Scanner sc = new Scanner(System.in);
    //Console console = System.console();
    System.out.println("\nEnter your name: ");
    String userName = sc.nextLine();
    writer.println(userName);

    String text;
    do {
      //text = console.readLine("[" + userName + "]: ");
      text = sc.nextLine();
      writer.println(text);

    } while (!text.equals("Exit"));

    try {
      socket.close();
    } catch (IOException ex) {
      System.out.println("Error writing to server: " + ex.getMessage());
    }
  }

}
