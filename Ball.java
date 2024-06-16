// Name: Yonatan Omer
// ID: 322624693

import biuoop.DrawSurface;
import java.awt.Color;

/**
 * Represents a ball in the animation.
 */
public class Ball implements Sprite {
    private static final double MOVE_SLIGHTLY = 0.2; // Constant for slight movement adjustment
    private Point center; // The center point of the ball
    private final int radius; // The radius of the ball
    private final Color color; // The color of the ball
    private Velocity velocity; // The velocity of the ball
    private final int minX; // The minimum x-coordinate value
    private final int boardWidth; // The width of the animation board
    private final int boardHeight; // The height of the animation board
    private final GameEnvironment gameEnvironment; // The game environment for collision detection

    /**
     * Constructor to initialize the ball.
     *
     * @param center       The center point of the ball
     * @param r            The radius of the ball
     * @param color        The color of the ball
     * @param minX         The minimum x-coordinate value

     * @param boardWidth   The width of the animation board
     * @param boardHeight  The height of the animation board
     * @param ge           The game environment for collision detection
     */
    public Ball(Point center, int r, Color color, int minX,
                int boardWidth, int boardHeight, GameEnvironment ge) {
        this.center = center; // Initialize the center point of the ball
        this.radius = r; // Initialize the radius of the ball
        this.color = color; // Initialize the color of the ball
        this.velocity = new Velocity(0, 0); // Initialize the velocity of the ball
        this.minX = minX; // Initialize the minimum x-coordinate value
        this.boardWidth = boardWidth; // Initialize the width of the animation board
        this.boardHeight = boardHeight; // Initialize the height of the animation board
        this.gameEnvironment = ge; // Initialize the game environment
    }

    /**
     * Get the x-coordinate of the center of the ball.
     *
     * @return The x-coordinate of the center
     */
    public double getX() {
        return this.center.getX();
    }

    /**
     * Get the y-coordinate of the center of the ball.
     *
     * @return The y-coordinate of the center
     */
    public double getY() {
        return this.center.getY();
    }

    /**
     * Get the center point of the ball.
     *
     * @return The center point of the ball
     */
    public Point getCenter() {
        return this.center;
    }

    /**
     * Get the radius of the ball.
     *
     * @return The radius of the ball
     */
    public int getSize() {
        return this.radius;
    }

    /**
     * Get the game environment of the ball.
     *
     * @return The game environment
     */
    public GameEnvironment getGameEnvironment() {
        return gameEnvironment;
    }

    /**
     * Notify the ball that a unit of time has passed.
     */
    @Override
    public void timePassed() {
        moveOneStep(); // Move the ball one step
    }

    /**
     * Draw the ball on the given DrawSurface.
     *
     * @param surface The DrawSurface on which to draw the ball
     */
    @Override
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color); // Set the color of the ball
        surface.fillCircle((int) getX(), (int) getY(), getSize()); // Draw the ball
    }

    /**
     * Set the velocity of the ball.
     *
     * @param v The velocity to set
     */
    public void setVelocity(Velocity v) {
        this.velocity = v; // Set the velocity of the ball
    }

    /**
     * Get the velocity of the ball.
     *
     * @return The velocity of the ball
     */
    public Velocity getVelocity() {
        return this.velocity;
    }

    /**
     * Set the velocity of the ball.
     *
     * @param dx The change in x-coordinate per step
     * @param dy The change in y-coordinate per step
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy); // Set the velocity of the ball
    }

    /**
     * Moves the ball one step according to its velocity, handling collisions if they occur.
     * If a collision is detected, adjusts the ball's position slightly before the collision point,
     * notifies the colliding object, and updates the ball's velocity accordingly.
     */
    public void moveOneStep() {
        // Calculate the potential new position
        Point nextCenter = this.velocity.applyToPoint(this.center);
        // Create a line representing the ball's trajectory
        Line trajectory = new Line(this.center.getX(), this.center.getY(), nextCenter.getX(), nextCenter.getY());
        // Get the closest collision information
        CollisionInfo closestCollision = this.gameEnvironment.getClosestCollision(trajectory);

        if (closestCollision != null) {
            // If there is a collision
            Point collisionPoint = closestCollision.collisionPoint();
            // Move the ball slightly before the collision point
            double dx = collisionPoint.getX() - this.center.getX();
            double dy = collisionPoint.getY() - this.center.getY();
            this.center = new Point(this.center.getX() + (dx * MOVE_SLIGHTLY),
                    this.center.getY() + (dy * MOVE_SLIGHTLY));
            // Notify the hit object and update the velocity
            Collidable hitObject = closestCollision.collisionObject();
            this.velocity = hitObject.hit(collisionPoint, this.velocity);
        } else {
            // Move the ball to the next position
            this.center = nextCenter;
        }
    }

    /**
     * Add the ball to the game.
     *
     * @param game The game to add the ball to
     */
    public void addToGame(Game game) {
        game.addSprite(this); // Add the ball to the game's sprites
    }
}
