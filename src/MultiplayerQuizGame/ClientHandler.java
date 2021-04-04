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
  private static final HashMap<Character, String> answers = new HashMap<>();


  public ClientHandler(Socket clientSocket, ArrayList<ClientHandler> clients) throws IOException {

    allQuestions =  Files.readAllLines(
            (Paths.get(
                "D:\\FFHS\\FS21\\FTOOP\\MultiplayerQuizGame\\src\\MultiplayerQuizGame\\qa.txt")));

    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    out = new PrintWriter(clientSocket.getOutputStream(), true);

    String question  = answers.get(0);
    answers.put('D', allQuestions.get(1));
    answers.put('D', allQuestions.get(2));
    answers.put('D', allQuestions.get(3));
    answers.put('D', allQuestions.get(4));
    answers.put('D', allQuestions.get(5));
    answers.put('D', allQuestions.get(6));
    answers.put('D', allQuestions.get(7));
    answers.put('D', allQuestions.get(8));
    answers.put('D', allQuestions.get(9));
    answers.put('D', allQuestions.get(10));

  }
  @Override
  public void run() {
    try {
      while (true) {
        String playerResponse = in.readLine();
        if (playerResponse.contains("Play")){
          out.println("--------------- Welcome to the Quiz-Game!------------------");
          out.println(allQuestions.get(1));
          out.println("Choose you answer: ");
          out.println(allQuestions.get(2));
          out.println(allQuestions.get(3));
          out.println(allQuestions.get(4));
          out.println(allQuestions.get(5));
          System.out.print("");
          out.println(allQuestions.get(7));
          out.println(allQuestions.get(8));

        }
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
