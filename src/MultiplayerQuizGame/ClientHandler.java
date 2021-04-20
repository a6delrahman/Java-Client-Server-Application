package MultiplayerQuizGame;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class ClientHandler implements Runnable {

  private static final String FILENAME = "/src/qa.txt";

  private  BufferedReader in;
  private  PrintWriter out;
  private List<String> allQuestions;
  private static final HashMap<Integer, String> answers = new HashMap<>();


  public ClientHandler(Socket clientSocket, ArrayList<ClientHandler> clients) throws IOException {

    allQuestions =  Files.readAllLines(
        (Paths.get(
            "D:\\FFHS\\FS21\\FTOOP\\MultiplayerQuizGame\\src\\MultiplayerQuizGame\\qa.txt")));

    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    out = new PrintWriter(clientSocket.getOutputStream(), true);


  }
  @Override
  public void run() {
    try {
      while (true) {

        String playerResponse = in.readLine();
        if (playerResponse.contains("Play")){
          out.println("--------------- Welcome to the Quiz-Game!------------------");
          out.println(allQuestions.get(0));
          out.println("Waehle eine Antwort: ");
          out.println(allQuestions.get(1));
          out.println(allQuestions.get(2));
          out.println(allQuestions.get(3));

          if(playerResponse.equals(answers.get(4))){
            out.println(answers.get(5));
            out.println("Waehle eine Antwort: ");
            out.println(allQuestions.get(6));
            out.println(allQuestions.get(7));
            out.println(allQuestions.get(8));
          }
          if(playerResponse.equals(answers.get(9))){
            out.println(answers.get(10));
            out.println("Waehle eine Antwort: ");
            out.println(allQuestions.get(11));
            out.println(allQuestions.get(12));
            out.println(allQuestions.get(13));
          }

        }
      }
    } catch (IOException e) {
      System.err.println("IOEXCEPTION in Client Handler");
      System.err.println(Arrays.toString(e.getStackTrace()));
    } finally {
      out.close();

    }
  }

}
