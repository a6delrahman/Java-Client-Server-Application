package MultiplayerQuizGame.Server;

import MultiplayerQuizGame.Client.ClientHandler;
import MultiplayerQuizGame.Colors;
import MultiplayerQuizGame.Player.Player;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class GameServer {

  private static final int PORT = 9292;
  private static final List<ClientHandler> clients = new ArrayList<>();
  private static final CopyOnWriteArrayList<Player> players = new CopyOnWriteArrayList<>();
  private static final ExecutorService pool = Executors.newFixedThreadPool(5);


  public static void main(String[] args){
    // Server verbinden:
    try (ServerSocket listener = new ServerSocket(PORT)) {
      System.out.println(Colors.GREEN + "listening...");
      System.out.println(Colors.GREEN + "waiting for all players to connect...");
      int count = 1;
      do {
        Socket client = listener.accept();
        ClientHandler clientThread = new ClientHandler(client, clients, players);
        System.out.println(Colors.PURPLE +
              "[SERVER] Player " + count++ + " " + client.getInetAddress().getHostAddress() + " connected");
        clients.add(clientThread);
        pool.execute(clientThread);
      }while (!pool.isShutdown());
    } catch (IOException e) {
      System.out.println("pool not ready");
    } finally {
      pool.shutdown();
    }
  }

}
