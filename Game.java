// Name: Yonatan Omer
// ID: 322624693

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The main game class that handles the game environment, sprites, and game logic.
 */
public class Game {
    // Collection of all sprites in the game
    private SpriteCollection sprites;
    // Environment that holds all collidable objects
    private GameEnvironment environment;
    // GUI for the game window
    private GUI gui;
    // Sleeper to control the frame rate
    private Sleeper sleeper;
    // Paddle controlled by the player
    private Paddle paddle;
    // Constant color for the paddle
    public static final Color VERY_LIGHT_RED = new Color(255, 102, 102, 255);
    // List to keep track of all balls in the game
    public static List<Ball> balls = new ArrayList<>();

    /**
     * Constructor to initialize the game.
     */
    public Game() {
        // Initialize the sprite collection
        this.sprites = new SpriteCollection();
        // Initialize the game environment with screen dimensions
        this.environment = new GameEnvironment(800, 600);
        // Initialize the GUI with the title and dimensions
        this.gui = new GUI("NEW GAME", this.environment.getScreenWidth(), this.environment.getScreenHeight());
        // Initialize the sleeper
        this.sleeper = new Sleeper();
        // Initialize the paddle with position, size, color, and keyboard sensor
        this.paddle = new Paddle(new Rectangle(new Point(400 - 60, 570),
                90, 23), VERY_LIGHT_RED, gui.getKeyboardSensor(), 800);
        // Add the paddle to the game
        paddle.addToGame(this);
    }

    /**
     * Add a collidable object to the game environment.
     *
     * @param c The collidable object to add
     */
    public void addCollidable(Collidable c) {
        // Add the collidable to the environment
        this.environment.addCollidable(c);
    }

    /**
     * Add a sprite to the sprite collection.
     *
     * @param s The sprite to add
     */
    public void addSprite(Sprite s) {
        // Add the sprite to the collection
        this.sprites.addSprite(s);
    }

    /**
     * Add a ball to the static list of balls.
     *
     * @param b The ball to add
     */
    public void addBallToList(Ball b) {
        // Add the ball to the list
        balls.add(b);
    }

    /**
     * Initialize a new game: create the Blocks, Balls, and Paddle,
     * and add them to the game.
     */
    public void initialize() {
        // Width of each block
        int blockWidth = 55;
        // Height of each block
        int blockHeight = 23;
        // Vertical gap where blocks start
        int heightGap = 120;
        // Number of balls to create
        int numOfBalls = 2;
        // Random object to generate random values
        Random random = new Random();

        // List to hold boundary blocks
        List<Collidable> blocks = new ArrayList<>();
        // Create and add the upper boundary block
        blocks.add(new Block(new Rectangle(new Point(0, 0), 800, 10), Color.BLACK));
        // Create and add the lower boundary block
        blocks.add(new Block(new Rectangle(new Point(0, 590), 800, 10), Color.BLACK));
        // Create and add the left boundary block
        blocks.add(new Block(new Rectangle(new Point(0, 10), 10, 580), Color.BLACK));
        // Create and add the right boundary block
        blocks.add(new Block(new Rectangle(new Point(790, 10), 10, 580), Color.BLACK));

        // Array of colors for the rows of blocks
        Color[] colors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.MAGENTA};
        // Screen width
        int screenWidth = this.environment.getScreenWidth();
        // Screen height
        int screenHeight = this.environment.getScreenHeight();

        // Loop to create rows of blocks
        for (int row = 0; row < 6; row++) {
            // Number of blocks in the current row
            int numBlocks = 12 - row;
            // Y-coordinate of the current row
            int y = heightGap + row * blockHeight;
            // Loop to create blocks in the current row
            for (int col = 0; col < numBlocks; col++) {
                // X-coordinate of the current block
                int x = screenWidth - 10 - (numBlocks - col) * blockWidth;
                // Upper-left point of the current block
                Point upperLeft = new Point(x, y);
                // Create the rectangle for the current block
                Rectangle rect = new Rectangle(upperLeft, blockWidth, blockHeight);
                // Create the block with the rectangle and color
                Block block = new Block(rect, colors[row]);
                // Add the block to the game
                block.addToGame(this);
            }
        }

        // Add boundary blocks to the environment
        for (Collidable block : blocks) {
            this.environment.addCollidable(block);
        }

        // Add boundary blocks as sprites to the game
        for (Collidable block : blocks) {
            if (Block.class.isAssignableFrom(block.getClass())) {
                this.sprites.addSprite((Block) block);
            }
        }

        // Loop to create balls
        for (int i = 0; i < numOfBalls; i++) {
            // Generate random X-coordinate for the ball's starting position
            double startX = 30 + random.nextDouble() * (650 - 30);
            // Generate random Y-coordinate for the ball's starting position
            double startY = 500 + random.nextDouble();
            // Create the ball with the starting position, radius, color, and environment
            Ball ball = new Ball(new Point(startX, startY), 8, Color.black, 0, 0, screenWidth, this.environment);
            // Set the velocity of the ball with a random angle and speed
            ball.setVelocity(Velocity.fromAngleAndSpeed(random.nextInt(360), 7));
            // Add the ball to the game
            ball.addToGame(this);
            // Add the ball to the list of balls
            addBallToList(ball);
        }
    }

    /**
     * Run the game -- start the animation loop.
     */
    public void run() {
        // Frames per second for the game
        int framesPerSecond = 60;
        // Milliseconds per frame to control frame rate
        int millisecondsPerFrame = 1000 / framesPerSecond;

        // Infinite loop to run the game
        while (true) {
            // Get the current time in milliseconds
            long startTime = System.currentTimeMillis(); // timing

            // Get the drawing surface
            DrawSurface d = this.gui.getDrawSurface();
            // Draw all sprites on the surface
            this.sprites.drawAllOn(d);
            // Show the drawn surface on the GUI
            this.gui.show(d);

            // Notify all sprites that time has passed
            this.sprites.notifyAllTimePassed();

            // Calculate the time used for drawing and updating
            long usedTime = System.currentTimeMillis() - startTime;
            // Calculate the remaining time to sleep to maintain frame rate
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            // Sleep for the remaining time if needed
            if (milliSecondLeftToSleep > 0) {
                this.sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }
}
