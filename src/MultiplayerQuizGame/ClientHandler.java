package MultiplayerQuizGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class ClientHandler implements Runnable {

  private final BufferedReader in;
  private final PrintWriter out;

  public ClientHandler(Socket clientSocket, ArrayList<ClientHandler> clients) throws IOException {
    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    out = new PrintWriter(clientSocket.getOutputStream(), true);
  }
  @Override
  public void run() {
    try {
      while (true) {

        String playerResponse = in.readLine();
        if (playerResponse.contains("Play")){
          out.println("Yeah!");
        }

        out.println("--------------- Welcome to the Quiz-Game!------------------");

      }
    } catch (IOException e) {
      System.err.println("IOEXCEPTION in Client Handler");
      System.err.println(Arrays.toString(e.getStackTrace()));
    } finally {
      out.close();
      try {
        in.close();
      }
      catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

}
