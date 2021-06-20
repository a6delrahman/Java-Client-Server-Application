package MultiplayerQuizGame.Client;

import MultiplayerQuizGame.FileHandler.FileHandler;
import MultiplayerQuizGame.Player.Player;
import MultiplayerQuizGame.Utils.Colors;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Client Handler Class is responsible for delivering the Questions to all Clients from the Server.
 * It sends the Questions depending on which Catalogue the user chooses at the beginning of the
 * Game. The Player can Choose from 3 different Quizzes at the beginning of the Game. Once the round
 * is finished, the Player has the choice of either quitting the Game or to play the quiz again.*
 *
 * @author Abdelrahman Abdelwahed
 */
public class ClientHandler implements Runnable {
  private final List<ClientHandler> clients;
  private static final SortedMap<String, Integer> playerScoreList = new TreeMap<>();
  private final BufferedReader in;
  private final PrintWriter out;

  private static final String DECO1 =
      "*************************************************************";

  private static final String DECO4 = "****************************";
  private static final String DECO3 =
      "======================================================================================";
  private static final String VERBRAUCHTEZEIT = "Verbrauchte Zeit: ";
  private FileHandler fh;



  private final Player player;
  /**
   * The Constructor has the parameters: Socket for connecting to the Server and a list of the
   * Clients (connections). The input and Output streams are instantiated to send and receive data
   * form the Server. In this case, depending on which quiz has been chosen, the corresponding
   * file will be passed to the FileHandler Class executed by the chooseQuizFile() Method.
   */
  public ClientHandler(Socket serverSocket, List<ClientHandler> clientConnections)
      throws IOException {

    this.clients = clientConnections;
    this.player = new Player("", 0);

    // in coming from server
    in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
    // out going to server
    out = new PrintWriter(serverSocket.getOutputStream(), true);
    getIntroScreen();
    out.println(
        Colors.YELLOW
            + "\t\t\t\tWILLKOMMEN ZUM SPIEL\n"
            + Colors.PURPLE
            + "\t\t\t\tWER WEISS DENN SOWASS?");
    out.println(DECO1 + "\n");
    out.println(
        Colors.BLUE
            + "\t\tBitte waehle eines der 3 Quiz aus. Auswahlmoeglichkeiten sind: 1, 2 oder 3");
    out.println("\t\t-----------------------------------------------");
    out.println(Colors.YELLOW + "\t\t\t1 - Quiz vom 2. Januar 2020");
    out.println("\t\t\t2 - Quiz vom 3. Januar 2020");
    out.println("\t\t\t3 - Quiz vom 7. Januar 2020");
  }

  @Override
  public synchronized void run() {
    try {
      do {
        // choosing the quiz file
        String opt = in.readLine();
        chooseQuizFile(opt);
        wait(500);
        out.println(
            Colors.CYAN
                + "\n\t\tHinweise:\n\t\t\t\t\t***\tDer Spieler mit der schnelleren, korrekten Antwort gewinnt."
                + "\n\t\t\t\t\t***\tDas Zeitlimit pro Frage betraegt 30 Sekunden.\n"
                + "\t\t\t\t\t***\tEs sind insgesamt 13 Fragen zu beantworten");
        out.println(DECO1 + "\n");
        wait(2000);
        out.println(Colors.PURPLE + "  Bitte gib deinen Namen ein");
        out.println(Colors.WHITE + DECO4);
        player.setName(in.readLine());
        wait(500);
        out.println(Colors.BLUE + "\n\nHallo " + player.getPlayerName());
        wait(1000);
        out.println(DECO4);
        out.println(Colors.GREEN + "Viel Glueck!");
        wait(1200);
        out.println("...... 3");
        wait(1000);
        out.println(".... 2");
        wait(1000);
        out.println("... 1");
        wait(1000);
        out.println(Colors.BLUE + DECO4 + "\n");

        // looping through the Questions Array and sending each String to the Client
        for (int i = 0; i < fh.getQuestions().size(); i++) {

          String question = nextQuestion(i);
          out.println(DECO3 + "\n");
          out.println(question + "\t\t\t\t");
          out.println(DECO3 + "\n");

          // Elapsed time for each player response
          Instant start = Instant.now();
          String playerInput = in.readLine();
          out.println("");
          Instant finish = Instant.now();
          long elapsedTime = Duration.between(start, finish).toMillis() / 1000;
          if (playerInput.equalsIgnoreCase(fh.getCorrAns(i))
              && playerInput.length() == 1
              && isValidInput(playerInput)
              && elapsedTime < 30) {
            player.incrementScore();
            out.println("\n\n" + Colors.GREEN + "\n\nAntwort ist richtig!\n");
            out.println(VERBRAUCHTEZEIT + elapsedTime + " sek");

          } else if (elapsedTime > 30) {

            out.println(Colors.RED + "Sorry, du warst zu spaet!\n");
            out.println(Colors.YELLOW + "die Antwort ist " + fh.getCorrAns(i));
            out.println(VERBRAUCHTEZEIT + +elapsedTime + " sek\n");
          } else if (!isValidInput(playerInput)) {
            out.println(Colors.YELLOW + "Eingabe nicht gueltig. Gueltige Eingaben sind: A, B oder C");
            out.println(Colors.PURPLE + VERBRAUCHTEZEIT + elapsedTime + " sek");
          } else {
            out.println(Colors.RED + "Antwort ist falsch!");
            out.println(Colors.YELLOW + "die richtige Antwort ist " + fh.getCorrAns(i));
            out.println(Colors.YELLOW + DECO4 + "\n");
            out.println(VERBRAUCHTEZEIT + elapsedTime + " sek\n");
          }
          playerScoreList.put(player.getPlayerName(), player.getPlayerScore());
          out.println(Colors.YELLOW + "_._._._._._._._._._._");
          out.println(Colors.CYAN + "Deine Punktenanzahl: " + player.getPlayerScore());
          out.println(Colors.YELLOW + "_._._._._._._._._._._");
          out.println(Colors.BLUE + "\nSpielstand\n" + getScoreTable());
          printScoreList();
        }
        // End of the Game
        player.resetScore();
        out.println(Colors.CYAN + "\n>>>>>>>>>>>>>>>>>>>>>>>>SPIELENDE<<<<<<<<<<<<<<<<<<<<<\n\n");
        wait(1500);
        out.println(Colors.PURPLE + "Danke fuers Spielen!\n");
        wait(1500);
        out.println(
            Colors.YELLOW
                + "Gib " + Colors.RED + " 'Quit' " + Colors.YELLOW + " ein um das Spiel zu beenden. Oder drucke " + Colors.CYAN + " 1, 2 oder 3 " + Colors.YELLOW + " um fortzufahren!\n");
        wait(500);
        String tmp = in.readLine();
        chooseQuizFile(tmp);
        getIntroScreen();
        out.println(Colors.BLUE + "Bitte waehle eines der 3 Quiz aus");
        out.println("Auswahlmoeglichkeiten sind: 1, 2 oder 3\n");
        out.println("\t\t-----------------------------------------------");
        out.println(Colors.WHITE + "\t\t\t1 - Quiz vom 2. Januar 2020");
        out.println("\t\t\t2 - Quiz vom 3. Januar 2020");
        out.println("\t\t\t3 - Quiz vom 7. Januar 2020");
      } while (!in.readLine().equals("quit"));

    } catch (IOException | NullPointerException | InterruptedException e) {
      Thread.currentThread().interrupt();
      System.out.println(
          Colors.YELLOW + "Spieler " + player.getPlayerName() + " hat das Spiel verlassen");
    }
  }

  /**
   * Choosing the desidred Quiz File for the game. The player's Choice (input) will be passed as a
   * String argument to the method. If an invalid choice has been entered, the default quiz File
   * will be Quiz number 1.
   *
   * @param quizNum String passed by Player as input.
   */
  public void chooseQuizFile(String quizNum) {
    try {
      String kat1 = "src/MultiplayerQuizGame/Fragen/fk_1.txt";
      String kat2 = "src/MultiplayerQuizGame/Fragen/fk_2.txt";
      String kat3 = "src/MultiplayerQuizGame/Fragen/fk_3.txt";
      if (quizNum.contains("1")) {
        this.fh = new FileHandler(kat1);
      } else if (quizNum.contains("2")) {

        this.fh = new FileHandler(kat2);
      } else if (quizNum.contains("3")) {

        this.fh = new FileHandler(kat3);
      } else { // if a non valid quiz choice has been entered, it will start the game with a default
               // quiz.
        out.println("ungueltige Eingabe. Per default wird Quiz 1 gewaehlt.");
        fh = new FileHandler(kat1);
      }
      // in case th Player quits before playing
    } catch (NullPointerException e) {
      System.out.println("kein Quiz wurde selektiert.");
    }
  }

  /** @return the Score Table of all players */
  private String getScoreTable() {
    StringBuilder table = new StringBuilder();
    table.append("--------------------------------------------------------\n");
    for (ClientHandler p : getClients()) {
      table
          .append(Colors.PURPLE)
          .append(p.player.getPlayerName())
          .append(" : ")
          .append(Colors.GREEN)
          .append(p.player.getPlayerScore())
          .append(" / ")
          .append(fh.getQuestions().size())
          .append("\t\t\t");
    }
    table.append("\n");
    table.append(Colors.BLUE + "--------------------------------------------------------");
    return table.append("\n").toString();
  }

  /** @return the list of Players (Clients) */
  public List<ClientHandler> getClients() {
    return clients;
  }

  /** @return The next Question to the Player */
  public String nextQuestion(int index) {
    String question;
    question = fh.questionsAndAnsToArray().get(index);
    return question;
  }
  /**
   * @param toValidate user input when answering the questions
   * @return Checks if the User input matches the possible answers A, B or C
   */
  public boolean isValidInput(String toValidate) {
    boolean isValid = false;
    String tmp = toValidate.toUpperCase();
    if (tmp.length() == 1
        && (tmp.equalsIgnoreCase("A") || tmp.equalsIgnoreCase("B") || tmp.equalsIgnoreCase("C")))
      isValid = true;

    return isValid;
  }
  /**
   * This is an Extra to the Game. It will Print Scores of all Players sorted from Highest Score
   * first into a text file calles ScoreList.txt.
   */
  @SuppressWarnings("uchecked")
  public static void printScoreList() throws IOException {
    String scoreListPath = "src/MultiplayerQuizGame/Score/ScoreList.txt";

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(scoreListPath))) {
      for (Map.Entry<String, Integer> entry : playerScoreList.entrySet()) {
        writer.write(entry.getKey() + "\t: \t" + entry.getValue());
        writer.write("\n-------------");
        writer.newLine();

      }
      writer.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
/**
 * Prints the Intro Screen
 * */
  public void getIntroScreen(){
    out.println(
        Colors.YELLOW
            + "************************************************************************************************************************************************************************************************************");

    out.println(
        Colors.PURPLE
            + "\n"
            + "                                   \\\\                    //     ||===========     ||||||||||\\\\\n"
            + "                                    \\\\        ==        //      ||                ||   |--|  \\\\\n"
            + "                                     \\\\      //\\\\      //       ||===========     ||   |__|  //\n"
            + "                                      \\\\    //  \\\\    //        ||                ||   |||||//\n"
            + "                                       \\\\  //    \\\\  //         ||                ||   \\\\    \n"
            + "                                        \\\\//      \\\\//          ||===========     ||    \\\\    \n");

    out.println(
        Colors.BLUE
            + "\n"
            + "               \\\\                      //    ||============     ||        ////////        ////////                ||||||||||||\\\\       ||=========       ||\\\\      ||      ||\\\\      ||    \n"
            + "                \\\\                    //     ||                 ||        ||              ||                      ||           \\\\      ||                || \\\\     ||      || \\\\     ||    \n"
            + "                 \\\\        ==        //      ||                 ||        ||              ||                      ||            \\\\     ||                ||  \\\\    ||      ||  \\\\    ||   \n"
            + "                  \\\\      //\\\\      //       ||===========      ||        ||||||||        ||||||||                ||             \\\\    ||=========       ||   \\\\   ||      ||   \\\\   ||   \n"
            + "                   \\\\    //  \\\\    //        ||                 ||              ||              ||                ||             //    ||                ||    \\\\  ||      ||    \\\\  ||   \n"
            + "                    \\\\  //    \\\\  //         ||                 ||              ||              ||                ||            //     ||                ||     \\\\ ||      ||     \\\\ ||   \n"
            + "                     \\\\//      \\\\//          ||===========      ||        ////////        ||||||||                |||||||||||||//      ||=========       ||      \\\\||      ||      \\\\||\n");

    out.println(
        Colors.PURPLE
            + "\n"
            + "                                                                       ////////      ///////////\\      \\\\                      //      // \\\\           ////////         \\\\\\\\\\\\\\\\\\\\\\\n"
            + "                                                                       ||           //          \\\\      \\\\                    //      //   \\\\         ||                        |//\n"
            + "                                                                       ||          //            \\\\      \\\\                  //      //     \\\\        ||                      ||||\n"
            + "                                                                       ||||||||   //              \\\\      \\\\      //\\\\      //      //       \\\\       ||||||||               ||\n"
            + "                                                                             ||   \\\\              //       \\\\    //  \\\\    //      //=========\\\\             ||              ||\n"
            + "                                                                             ||    \\\\            //         \\\\  //    \\\\  //      //           \\\\            ||              O\n"
            + "                                                                       //////||     \\\\////////////           \\\\//      \\\\//      //             \\\\     ////////              O\n");

    out.println(
        Colors.YELLOW
            + "************************************************************************************************************************************************************************************************************\n");


  }

}
