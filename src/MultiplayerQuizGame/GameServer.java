package MultiplayerQuizGame;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameServer {

  private static final int PORT = 9292;
  private static ArrayList<ClientHandler> clients = new ArrayList<>();
  private static ExecutorService pool = Executors.newFixedThreadPool(5);

  public static void main(String[] args) throws IOException {

    // Server verbinden:
    ServerSocket listener = new ServerSocket(PORT);
    try {
      int count = 1;
      System.out.println("listening...");
      System.out.println("waiting for all players to connect");
      while (true) {

        Socket client = listener.accept();
        ClientHandler clientThread = new ClientHandler(client, clients);
        PrintWriter clientWriter = new PrintWriter(client.getOutputStream(), true);
        System.out.println("[SERVER] Player " + count++ + " connected");
        System.out.println("hello " + client.getInetAddress().getHostAddress());
        clients.add(clientThread);
        pool.execute(clientThread);
        if (clients.size() == 5){
          System.out.println("all players connected");
        }
      }
    } catch (IOException e) {
      e.printStackTrace();

    }
  }
}
