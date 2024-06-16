// Name: Yonatan Omer
// ID: 322624693

/**
 * This class represents the main entry point for running the game.
 * It creates a new instance of the Game class, initializes it, and runs the game.
 */
public class Ass3Game {

    /**
     * The main method to start the game.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        // Create a new instance of the Game class
        Game game = new Game();
        // Initialize the game
        game.initialize();
        // Run the game
        game.run();
    }
}
