package MultiplayerQuizGame;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameServer {


  private static final int PORT = 9292;
  private static final ArrayList<ClientHandler> clients = new ArrayList<>();
  private static final ExecutorService pool = Executors.newFixedThreadPool(5);

  public static void main(String[] args) throws IOException {
    // Server verbinden:
    try (ServerSocket listener = new ServerSocket(PORT)) {

      while (true){

        System.out.println("[SERVER] Waiting for Player connection...");
        Socket client = listener.accept();
        System.out.println("[SERVER] Connected to Player Client!");
        ClientHandler clientThread = new ClientHandler(client, clients);
        clients.add(clientThread);

        pool.execute(clientThread);
        System.out.println("Connected");
      }
    }
  }
}




