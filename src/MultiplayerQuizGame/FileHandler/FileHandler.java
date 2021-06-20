package MultiplayerQuizGame.FileHandler;

import MultiplayerQuizGame.Utils.Colors;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * This Class Handles the Quiz Question & Answer Files which consist of .txt files. The Algorithm splits
 * the Questions and answers into 2 separate Arrays using the FileHAndler Constructor.
 *
 * @author Abdelrahman Abdelwahed
 * */
public class FileHandler {

  private final ArrayList<String> allData = new ArrayList<>();
  private final CopyOnWriteArrayList<String> questions = new CopyOnWriteArrayList<>();
  private final CopyOnWriteArrayList<String> answers = new CopyOnWriteArrayList<>();
  private final List<Character> correctAnswers = new ArrayList<>();

 /**
  * Constructor takes the Path of Quiz File and is then Scanned and the splitting of Q&A starts by iterating
  * twice. When a Line starts with "Frage" it should take the next line as a question and loop once more
  * over the net 3 lines to get the options(answers) for each question. While that happening, if a
  * "*" is found in the next 3 lines it should add that character in the Correct Answers Array. All
  *  characters that had a "*" will will be replaced with a character and an empty String replacing that
  *   "*".
  * */
  public FileHandler(String path) {
    try {

      Scanner katReader = new Scanner(new File(path));
      while (katReader.hasNextLine()) {
        String frage = katReader.nextLine();
        // 1st iteration
        if (frage.contains("Frage")) {
          frage = katReader.nextLine();
          questions.add(frage);
          //2nd iteration
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
      System.out.println(Colors.RED + e.getMessage());
    }
  }

/**
 * Returns List of Q&A by iterating over the questions Array to append the Questions then it will iterate
 * over the answers to append them beneath..
 * */
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

  /**
   * @param i the index to the correct answer to each question.
   * @return the correct answer to the corresponding question
   * */

  public String getCorrAns(int i) {
    char ans = correctAnswers.get(i);
    return String.valueOf(ans);
  }

  /**
   * Returns all Questions only.
   * */

  public List<String> getQuestions() {
    return questions;
  }

}
