package MultiplayerQuizGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.Socket;

public class ServerConnection implements Runnable {

  private BufferedReader in;

  private Socket server;

  public ServerConnection(Socket s) throws IOException {
    server = s;
    in = new BufferedReader(new InputStreamReader(server.getInputStream()));
  }

  @Override
  public void run() {
    try {
      while (true) {
        String serverResponse = in.readLine();
        if (serverResponse == null) break;

        System.out.println("from server " + serverResponse);
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
