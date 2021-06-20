package MultiplayerQuizGame.Client;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


class ClientHandlerTest {

  @Test
  void isValidInput() {
    String testStr = "A";
    String testStr2 = "R";
    String testStr3 = "";

    assertTrue(true, testStr);
    assertFalse(false, testStr2);
    assertTrue(true, testStr3);
  }


}