package MultiplayerQuizGame.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerConnectionHandler implements Runnable {

  private final BufferedReader in;
  private final Socket server;

  public ServerConnectionHandler(Socket s) throws IOException {
    server = s;
    in = new BufferedReader(new InputStreamReader(server.getInputStream()));
  }

  @Override
  public void run() {
    try {
      while (true) {
        String serverResponse = in.readLine();
        if (serverResponse == null) break;

        System.out.println(serverResponse);

      }
    } catch (IOException e) {
      System.err.println("server disconnected");
    } finally {
      try {
        in.close();
        server.close();
      } catch (NullPointerException | IOException e) {
        e.printStackTrace();
      }
    }
  }
}
