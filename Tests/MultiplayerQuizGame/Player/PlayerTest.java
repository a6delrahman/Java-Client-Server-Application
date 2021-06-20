package MultiplayerQuizGame.Player;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PlayerTest {


  @Test
  void getPlayerScore() {
    Player p1 = new Player("Tom", 10);
    assertEquals(10, p1.getPlayerScore());
    assertNotEquals(5, p1.getPlayerScore());
  }

  @Test
  void getPlayerName() {
    Player p2 = new Player("Tom", 10);
    assertEquals("Tom", p2.getPlayerName());
  }
}