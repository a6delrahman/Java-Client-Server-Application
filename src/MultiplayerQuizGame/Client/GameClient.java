package MultiplayerQuizGame.Client;

import MultiplayerQuizGame.Server.ServerConnectionHandler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class GameClient {
  public static final String SERVER_IP = "127.0.0.1";
  public static final int PORT = 9292;

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
      System.out.println("disconnected");
    }


  }
}
