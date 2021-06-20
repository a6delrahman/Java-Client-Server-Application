package MultiplayerQuizGame.Server;

import MultiplayerQuizGame.Utils.Colors;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This Class handles the multiple connections to the Server. It prints out all the responses from
 * the Server to all clients at the same time. The input Stream sends the data to the Output stream of the Game Client Class.
 *
 * @author Abdelrahman Abdelwahed
 * */
public class ServerConnectionHandler implements Runnable {

  private final BufferedReader in;
  private StringBuilder progress = new StringBuilder(60); //initial Capacity for the Strng builder
  private final AtomicBoolean shouldExit;
  private final Socket server;

/**
 * Constructor for instantiating a connection between a Client an Server using Socket s and initializing/opening the input stream.
 * shouldExit Atomic Boolean is used for Thread Safety instead of using true as a condition.
 *
 * @param s Listening Socket
 *
 * */

  public ServerConnectionHandler(Socket s) throws IOException {
    server = s;
    in = new BufferedReader(new InputStreamReader(s.getInputStream()));
    shouldExit = new AtomicBoolean(false);
  }

  @Override
  public void run() {
    System.out.println(Colors.GREEN + "\n\nQuiz Fragen werden geladen...");
    try {
      System.out.println(Colors.YELLOW + "");
      for (int i = 0; i < 50; i++) {
        loading(i, 99);
        Thread.sleep(60);
      }
      System.out.println("\n");

      while (!shouldExit.get()) {
        String serverResponse = in.readLine();
          // close stream and socket when quit has been typed
        if (serverResponse.equals("quit")) {
          server.close();
          in.close();
        }
        System.out.println(serverResponse);
      }//message to be displayed when player disconnects or quits
    } catch (IOException | InterruptedException e) {
      Thread.currentThread().interrupt();
      System.out.println("user quit the game");
    }finally{
      try {
        server.close();
        in.close();
      } catch (NullPointerException | IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * This method prints a progress bar once the Client has connected and the Game starts to load.
   * @param start Begin of loading progress
   * @param total Total loading Progress
   *
   * */

  public void loading(int start, int total){

    for (int i = 0; i < 50; i++) {
      char[] chars = {'|', '/', '-', '\\'};
      String format = "\r%3d%% %s %c";
      int percent = (++start * 100) / total;
      int extraChars = (percent / 2) - this.progress.length();

      while (extraChars-- > 0) {
        progress.append('#');
      }
      System.out.printf(format, percent, progress, chars[start % chars.length]);
      if (start == total) {
        System.out.flush();
        System.out.println();
        this.progress = new StringBuilder(20);
      }
    }
  }
}

