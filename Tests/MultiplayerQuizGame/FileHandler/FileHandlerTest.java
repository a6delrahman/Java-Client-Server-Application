package MultiplayerQuizGame.FileHandler;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileHandlerTest {
  FileHandler fH =
      new FileHandler("MultiplayerQuizGame/Fragen/fk_1.txt");
  @Test
  void questionsAndAnsToArray() {
    FileHandler fH =
        new FileHandler("MultiplayerQuizGame/Fragen/fk_2.txt");

    List<String> tmpArr = fH.getQuestions();
    assertEquals("Welche besondere Eigenschaft zeichnet hydraulischen Moertel aus?", tmpArr.get(5));

  }

  @Test
  void getCorrAns() {
    FileHandler fH =
        new FileHandler("MultiplayerQuizGame/Fragen/fk_1.txt");

    assertEquals("C", fH.getCorrAns(0));
    assertEquals("A", fH.getCorrAns(5));

  }
}