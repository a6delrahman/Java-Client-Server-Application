package MultiplayerQuizGame.FileHandler;

import MultiplayerQuizGame.Utils.Colors;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * To handle files
 * */
public class FileHandler {

  private final ArrayList<String> allData = new ArrayList<>();
  private final CopyOnWriteArrayList<String> questions = new CopyOnWriteArrayList<>();
  private final CopyOnWriteArrayList<String> answers = new CopyOnWriteArrayList<>();
  private final List<Character> correctAnswers = new ArrayList<>();

  public FileHandler(){}


  public FileHandler(Path path) {
    try {
      File fragenKat = new File(String.valueOf(path));
      Scanner katReader = new Scanner(fragenKat);
      while (katReader.hasNextLine()) {
        String frage = katReader.nextLine();
        if (frage.contains("Frage")) {
          frage = katReader.nextLine();
          questions.add(frage);
          for (int j = 0; j <= 2; j++) {
            String options = katReader.nextLine();
            if (options.contains("*")) {
              correctAnswers.add(options.charAt(0));
              options = options.replace("*", "");
            }
            answers.add(options);
          }
        }
      }
      questionsAndAnsToArray();
      katReader.close();

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }


  public List<String> questionsAndAnsToArray() {
    int index = 0;
    for (String question : questions) {
      StringBuilder strB = new StringBuilder();
      strB.append(Colors.CYAN).append(question).append("\n");
      for (int j = 0; j < 3; j++) {
        strB.append("\n");
        strB.append(Colors.PURPLE + "\t\t\t\t").append(answers.get(index)).append("\n");
        index++;
      }
      String tString = strB.toString();
      allData.add(tString);
    }
    return allData;
  }

  public String getCorrAns(int i) {
    char ans = correctAnswers.get(i);
    return String.valueOf(ans);
  }

  public List<String> getQuestions() {
    return questions;
  }

}
