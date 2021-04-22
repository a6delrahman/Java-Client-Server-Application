package MultiplayerQuizGame;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class ClientHandler implements Runnable {

  private final BufferedReader in;
  private final PrintWriter out;
  private static ArrayList<ClientHandler> clients;
  private Socket socket;
  private static final String FILENAME = "/src/qa.txt";
  private static List<String> allQuestions;

  public ClientHandler(Socket serverSocket, ArrayList<ClientHandler> clientConnections)
      throws IOException {
    this.clients = clientConnections;
    this.socket = serverSocket;
    in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
    out = new PrintWriter(serverSocket.getOutputStream(), true);
    allQuestions =
        Files.readAllLines(
            (Paths.get(
                "D:\\FFHS\\FS21\\FTOOP\\MultiplayerQuizGame\\src\\MultiplayerQuizGame\\qa.txt")));
  }

  @Override
  public void run() {
    try {
      while (true) {
        out.println("-----------------------------------------------------------");
        out.println("***************** Welcome to the Quiz-Game!*****************");
        out.println("-----------------------------------------------------------");
        out.println("Type play to start");

        String playString = in.readLine();

        if (playString.contains("play")) {
          out.println("Frage 1: ");
          out.println(getLine(0));
          out.println("Waehle eine Antwort: ");

          sendToAllClients(getLine(1));
          sendToAllClients(getLine(2));
          sendToAllClients(getLine(3));
        }

        String answer1FromClient = in.readLine();

        if (answer1FromClient.equals("A")) {
          out.println("Correct");
          out.println("      " + allQuestions.get(4));
          out.println("      " + allQuestions.get(5));
          out.println("      " + allQuestions.get(6));
          out.println("      " + allQuestions.get(7));
        } else {
          System.out.println("incorrect");
        }

        String answer2FromClient = in.readLine();

        if (answer2FromClient.equals("C")) {
          out.println("Correct");
          out.println("      " + allQuestions.get(8));
          out.println("      " + allQuestions.get(9));
          out.println("      " + allQuestions.get(10));
          out.println("      " + allQuestions.get(11));
        }
      }
    } catch (IOException e) {
      System.out.println("server disconnected");
    } finally {
      try {
        out.close();
        in.close();
        System.err.println("disconnected");
        System.exit(0);
      } catch (NullPointerException | IOException e) {
        System.out.println("disconnected");
      }
    }
  }

  public static String getLine(int index) {
    return allQuestions.get(index);
  }

  public static void sendToAllClients(String s) {
    for (ClientHandler client : clients) {
      client.out.println(s);
    }
  }

  public static void sendStringToClient(String text) {
    System.out.println(text);
  }
}
