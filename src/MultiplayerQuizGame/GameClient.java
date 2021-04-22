package MultiplayerQuizGame;

// -----> Import der bentigten Klassen:

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class GameClient {
  public static final String SERVER_IP = "127.0.0.1";
  public static final int PORT = 9292;

  public static void main(String[] args) throws IOException {
    Socket socket = new Socket(SERVER_IP, PORT);

    ServerConnection serverCon = new ServerConnection(socket);

    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

    new Thread(serverCon).start();

    while (true) {

      String command = in.readLine();
      if (command.equals("quit")) {
        socket.close();
        out.println("last player disconnected");
        System.exit(0);
      }

      out.println(command); // out coming from server
    }
  }
}
