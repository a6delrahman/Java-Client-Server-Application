package MultiplayerQuizGame.Client;

import MultiplayerQuizGame.Colors;
import MultiplayerQuizGame.FileHandler.FileHandler;
import MultiplayerQuizGame.Player.Player;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


public class ClientHandler extends FileHandler implements Runnable {
  private final List<ClientHandler> clients;
  private final List<Player> playerList;
  private final BufferedReader in;
  private final PrintWriter out;
  private final FileHandler fh;
  private static final Path myPath =
      Paths.get(
          "D:\\FFHS\\FS21\\FTOOP\\MultiplayerQuizGame\\src\\MultiplayerQuizGame\\Fragen\\fk_1.txt");
  private final Player player;

  public ClientHandler(
      Socket serverSocket,
      List<ClientHandler> clientConnections,
      List<Player> playerList
      )
      throws IOException {
    super(myPath);

    this.clients = clientConnections;
    this.playerList = playerList;
    this.player = new Player("", 0);

    // in from server
    in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
    // out to server
    out = new PrintWriter(serverSocket.getOutputStream(), true);
    fh = new FileHandler(myPath);
  }

  @Override
  public void run() {
    try {
      long startTime = System.currentTimeMillis();
      long timeElapsed = 0;

      do {

        out.println(Colors.YELLOW + "************************************************************************************************************************************************************************************************************");

        out.println(
            Colors.PURPLE
                + "\n"
                + "                                   \\\\                    //     ||===========     ||||||||||\\\\\n"
                + "                                    \\\\        ==        //      ||                ||   |--|  \\\\\n"
                + "                                     \\\\      //\\\\      //       ||===========     ||   |__|  //\n"
                + "                                      \\\\    //  \\\\    //        ||                ||   |||||//\n"
                + "                                       \\\\  //    \\\\  //         ||                ||   \\\\    \n"
                + "                                        \\\\//      \\\\//          ||===========     ||    \\\\    \n");

        out.println(Colors.BLUE + "\n"
            + "               \\\\                      //    ||============     ||        ////////        ////////                ||||||||||||\\\\       ||=========       ||\\\\      ||      ||\\\\      ||    \n"
            + "                \\\\                    //     ||                 ||        ||              ||                      ||           \\\\      ||                || \\\\     ||      || \\\\     ||    \n"
            + "                 \\\\        ==        //      ||                 ||        ||              ||                      ||            \\\\     ||                ||  \\\\    ||      ||  \\\\    ||   \n"
            + "                  \\\\      //\\\\      //       ||===========      ||        ||||||||        ||||||||                ||             \\\\    ||=========       ||   \\\\   ||      ||   \\\\   ||   \n"
            + "                   \\\\    //  \\\\    //        ||                 ||              ||              ||                ||             //    ||                ||    \\\\  ||      ||    \\\\  ||   \n"
            + "                    \\\\  //    \\\\  //         ||                 ||              ||              ||                ||            //     ||                ||     \\\\ ||      ||     \\\\ ||   \n"
            + "                     \\\\//      \\\\//          ||===========      ||        ////////        ||||||||                |||||||||||||//      ||=========       ||      \\\\||      ||      \\\\||\n");

        out.println(Colors.PURPLE + "\n"
            + "                                                                       ////////      ///////////\\      \\\\                      //      // \\\\           ////////      ////////        \\\\\\\\\\\\\\\\\\|                                                      \n"
            + "                                                                       ||           //          \\\\      \\\\                    //      //   \\\\         ||            ||               ||      ||                                                   \n"
            + "                                                                       ||          //            \\\\      \\\\                  //      //     \\\\        ||            ||                     ||                                                      \n"
            + "                                                                       ||||||||   //              \\\\      \\\\      //\\\\      //      //       \\\\       ||||||||      ||||||||             ||||                                                        \n"
            + "                                                                             ||   \\\\              //       \\\\    //  \\\\    //      //=========\\\\             ||            ||            |                                                           \n"
            + "                                                                             ||    \\\\            //         \\\\  //    \\\\  //      //           \\\\            ||            ||            OO                                                          \n"
            + "                                                                       //////||     \\\\////////////           \\\\//      \\\\//      //             \\\\     ////////      ||||||||            OO\n");


        out.println(Colors.YELLOW + "************************************************************************************************************************************************************************************************************\n");

        out.println(Colors.CYAN + "The Game will start once you submit your name and when all player are connected!");
        out.println(Colors.RED + "You have got 30 seconds to answer each question. If Time runs up, you will automatically receive the next Question\nand 0 Points will be added to you r Score.");
        out.println(Colors.PURPLE + "  Please Type your Name to Start");

        player.setName(in.readLine());

        out.println("Hello " + player.getPlayerName());
        out.println("Your Score: " + player.getPlayerScore());

          for (int i = 0; i < getQuestions().size(); i++) {
          String question = nextQuestion(i);
          out.println(question);
          String playerInput = in.readLine();


//          if (playerInput.equalsIgnoreCase("a") || playerInput.equalsIgnoreCase("b") || playerInput.equalsIgnoreCase("c")){
//            continue;
//          }

          if (playerInput.equals(getCorrAns(i))) {
            out.println("correct");
            out.println("Point for you");
            player.incrementScore();
          } else {
            out.println(Colors.RED +  "INCORRECT / -1 Pt.");
            out.println(Colors.YELLOW + "Correct answer is " + getCorrAns(i));
            player.decrementScore();
          }

          out.println("Current Score Board :\n" + getScoreTable());

        }
        out.println("End of Game  " + player.getPlayerName());

      } while (!in.readLine().contains("quit"));
      out.println("Type quit to leave");
    } catch (IOException | NullPointerException e) {
      e.printStackTrace();
      System.out.println("Player disconnected");

    } finally {
      try {
        if (in.readLine().contains("quit")) {
          out.close();
          in.close();
        }

      } catch (NullPointerException | IOException e) {
        System.out.println("player disconnected or has quit the game");
      }
    }
  }

  private String getScoreTable() {
    StringBuilder table = new StringBuilder();
    table.append("--------------------------------------------------------\n");
    table.append(Colors.GREEN +  "Scores: \n");

    for (ClientHandler p : getClients()) {
      table.append(p.player.getPlayerName()).append(" : ").append(p.player.getPlayerScore()).append("\n");
    }
    table.append("--------------------------------------------------------");

    return table.append("\n").toString();
  }



  public List<ClientHandler> getClients() {
    return clients;
  }

  public String nextQuestion(int index) {

    String question;
    question = fh.questionsAndAnsToArray().get(index);

    return question;
  }

  public boolean isValidInput(String userInput) {
    return (userInput.length() == 1) && (userInput.contains("A") || userInput
        .contains("B") || userInput.contains("C"));
  }

  public boolean allPlayersReady() {
    boolean isSet = false;
    try {
      if (clients.size() <= 5) {
        isSet = true;
      }
    } catch (Exception e) {
      System.out.println("waiting for all players to connect");
      System.out.println("current player pool size " + clients.size());
      System.out.println("Check player count and start new players");
    }
    return isSet;
  }
}
