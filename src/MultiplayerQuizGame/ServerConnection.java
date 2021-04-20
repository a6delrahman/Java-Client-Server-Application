package MultiplayerQuizGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerConnection implements Runnable {

  private final BufferedReader in;

  public ServerConnection(Socket s) throws IOException {

    in = new BufferedReader(new InputStreamReader(s.getInputStream()));

  }

  @Override
  public void run() {
    try {
      while (true) {

        String serverResponse = in.readLine();
        if (serverResponse == null) break;
        System.out.println(serverResponse);

      }

    }  catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        in.close();
      } catch (IOException ioException) {
        ioException.printStackTrace();
      }
    }
  }
}
