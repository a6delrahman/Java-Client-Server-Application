package MultiplayerQuizGame;

// -----> Import der benötigten Klassen:

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
// <-----

/**
 * last change: 04.11.2004
 * run: java -cp class beispiel1.DemoClient_1 127.0.0.1 1111
 */
public class GameClient {
  public static final String SERVER_IP = "127.0.0.1";
  public static final int PORT = 9292;

  public static void main(String[] args) throws IOException {
  Socket socket = new Socket(SERVER_IP, PORT);

  ServerConnection serverCon = new ServerConnection(socket);

  BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
  PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

  new Thread(serverCon).start();

  while (true){

    System.out.println("> Type Play, or quit to leave");
    String command  = keyboard.readLine();

    if (command.contains("quit")) break;

    out.println(command);

    String serverResponse = keyboard.readLine();
    System.out.println("server says: " + serverResponse);
  }
  socket.close();
  System.exit(0);
  }
}