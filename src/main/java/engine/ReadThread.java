package engine;

import java.io.*;
import java.net.*;

public class ReadThread extends Thread {

  private BufferedReader reader;

  public ReadThread(Socket socket) {
    try {
      InputStream input = socket.getInputStream();
      reader = new BufferedReader(new InputStreamReader(input));
    } catch (IOException ex) {
      System.out.println("Error getting input stream: " + ex.getMessage());
      ex.printStackTrace();
    }
  }

  public void run() {
    while (true) {
      try {
        String response = reader.readLine();
        System.out.println("\n" + response);
      } catch (IOException ex) {
        System.out.println("Socket closed: ");
        ex.printStackTrace();
        break;
      }
    }
  }

}
