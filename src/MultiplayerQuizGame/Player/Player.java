package MultiplayerQuizGame.Player;

/** The Player Class containing the Player name and Score. Index represents the Player count. */
public class Player {

  private String playerName;
  private int index;
  private int score;

  // Player initialisation
  public Player(String playerName, int score) {
    this.playerName = "Player" + index;
    index++;
    this.playerName = playerName;
    this.score = score;
  }
  /**
   * Resets the score of Player after playing the first round, in case he/ she decides to play
   * again. The Score will be reset back to 0.
   */
  public void resetScore() {
    score = 0;
  }
  // getters and setters
  public String getPlayerName() {
    return playerName;
  }

  public void setName(String name) {
    this.playerName = name;
  }

  public int getPlayerScore() {
    return score;
  }

  /** increment score by 1 point */
  public void incrementScore() {
    score++;
  }

}
