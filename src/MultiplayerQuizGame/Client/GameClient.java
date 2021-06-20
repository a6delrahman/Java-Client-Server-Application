
package MultiplayerQuizGame.Client;

import MultiplayerQuizGame.Server.ServerConnectionHandler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This Class represents the Client/Player. It has a Socket to connect to the Server on the same TCP PORT 9292.
 * The ServerConnectionHandler Object connects to the Client over the same Socket. The data will be
 * sent in and out using the BufferedReader und Printwriter respectively to all client Threads. Once
 * all Objects have been initialised, the Client will pass the Server Connection as thread and start it.
 * As soon as "quit has been typed, the Clients will disconnect."
 * */

public class GameClient {
  private static final String SERVER_IP = "127.0.0.1";
  private static final int PORT = 9292;

  public static void main(String[] args) throws IOException {
    AtomicBoolean shouldExit = new AtomicBoolean(false);
    Socket socket = new Socket(SERVER_IP, PORT);
    ServerConnectionHandler serverCon = new ServerConnectionHandler(socket);
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

    new Thread(serverCon).start();

    try{
       while (!shouldExit.get()) {
        String command = in.readLine();
        if (command.equals("quit")) {
          socket.close();
          in.close();
          out.println("last player disconnected");
        }
        out.println(command); // out coming from server
      }
      }catch (IOException e){
      System.out.println(e.getLocalizedMessage());
    }



  }
}
