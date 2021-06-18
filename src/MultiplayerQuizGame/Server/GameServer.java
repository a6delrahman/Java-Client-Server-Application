package MultiplayerQuizGame.Server;

import MultiplayerQuizGame.Client.ClientHandler;
import MultiplayerQuizGame.Utils.Colors;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/** This Class represents the implemention of a Multithreaded Server which communicates over the ServerSocket @listener is constantly listening for Client connections.
 * The Communication between Client and Server will be using the TCP PORT 9292. Once the Client has successfully connected, the data/information will be send to the Client.
 * By starting the main thread in this class, the Executor Service will execute the Thread Pool by spawing each Client connection into a Thread. Every connected Client will
 * be added to the List of Clients. Each Client Thread is instantiated as a ClientHandler Object with a Socket and a List of Clients as arguments.
 * If the connection was Successfull the "Player Connected" message will be displayed on the Console. When the Thread Pool finishes executing all Tasks, the Thread Pool will be shut down.
 *
 * The ServerConnectionHandler Class will be responsible for the handling of each client connection to the Server. *
 *
 * @author Abdelrahman Abdelwahed
 * */
public class GameServer {

  private static final int PORT = 9292;
  private static final List<ClientHandler> clients = new ArrayList<>();
  private static final ExecutorService pool = Executors.newFixedThreadPool(5);

  public static void main(String[] args) {
    // Verbindung wird initialisiert auf TCP Port 9292
    try (ServerSocket listener = new ServerSocket(PORT)) {
      AtomicBoolean shouldExit = new AtomicBoolean(false);
      int count = 1;
      System.out.println(Colors.GREEN + "listening...");
      System.out.println(Colors.GREEN + "waiting for players to connect...");
      while (!shouldExit.get()) {
        Socket client = listener.accept();
        ClientHandler clientThread = new ClientHandler(client, clients);
        System.out.println(
            Colors.PURPLE
                + "[SERVER] Player "
                + count++
                + " "
                + client.getInetAddress().getHostAddress()
                + " connected");
        clients.add(clientThread);
        pool.execute(clientThread);
      }
      pool.shutdown();

    } catch (IOException e) {
      System.out.println("pool not ready");
      System.exit(0);
    } finally {
      pool.shutdownNow();
      Thread.currentThread().interrupt();

    }
  }
}
