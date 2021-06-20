package MultiplayerQuizGame.Utils;
/**
 * Utility Class for colour codes. To have colour variation on the console output.
 * */

public class Colors {
  private Colors(){
    throw new IllegalArgumentException("error");
  }
  public static final String RED = "\033[0;31m";     // RED
  public static final String GREEN = "\033[0;32m";   // GREEN
  public static final String YELLOW = "\033[0;33m";  // YELLOW
  public static final String BLUE = "\033[0;34m";    // BLUE
  public static final String PURPLE = "\033[0;35m";  // PURPLE
  public static final String CYAN = "\033[0;36m";    // CYAN
  public static final String WHITE = "\033[0;37m";   // WHITE
}
