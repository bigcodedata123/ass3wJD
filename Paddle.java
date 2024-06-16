// Name: Yonatan Omer
// ID: 322624693

import biuoop.DrawSurface;
import java.awt.Color;

import biuoop.KeyboardSensor;
/**
 * The Paddle class defines the paddle object in the game, which the player controls using the keyboard.
 * It implements both the Sprite and Collidable interfaces.
 */

public class Paddle implements Sprite, Collidable {
    private KeyboardSensor keyboard;
    private Rectangle paddleShape;
    private final Color color;
    private int gameWidth;
    public static final double EPSILON = 0.001;

    /**
     * Helper method to compare two doubles for equality within a small margin.
     *
     * @param a       The first double to compare
     * @param b       The second double to compare
     * @param epsilon The precision threshold
     * @return True if the doubles are considered equal within the threshold, false otherwise
     */
    public static boolean threshold(double a, double b, double epsilon) {
        return Math.abs(a - b) < epsilon;
    }

    /**
     * Constructor for creating a Paddle object.
     *
     * @param paddleShape The shape (rectangle) of the paddle
     * @param color       The color of the paddle
     * @param keyboard    The keyboard sensor to control the paddle
     * @param gameWidth   The width of the game screen
     */
    public Paddle(Rectangle paddleShape, Color color, KeyboardSensor keyboard, int gameWidth) {
        this.paddleShape = paddleShape;
        this.color = color;
        this.keyboard = keyboard;
        this.gameWidth = gameWidth;
    }

    /**
     * Moves the paddle left by 7 pixels.
     * If the paddle reaches the left edge of the screen, it wraps around to the right edge.
     */
    public void moveLeft() {
        double newX = this.paddleShape.getUpperLeft().getX() - 7; // Move left by 7 pixels
        double newY = this.paddleShape.getUpperLeft().getY(); // Y coordinate remains the same
        if (newX < 10) {
            newX = gameWidth - 10 - paddleShape.getWidth(); // Set newX to the rightmost position
        }
        Rectangle newPaddle = new Rectangle(new Point(newX, newY),
                this.paddleShape.getWidth(), this.paddleShape.getHeight());
        // Check for collisions with balls
        for (Ball ball : Game.balls) {
            if (newPaddle.contains(ball.getCenter())) {
                return;
            }
        }
        // Update paddle's position
        this.paddleShape = newPaddle;
    }

    /**
     * Moves the paddle right by 7 pixels.
     * If the paddle reaches the right edge of the screen, it wraps around to the left edge.
     */
    public void moveRight() {
        double newX = this.paddleShape.getUpperLeft().getX() + 7; // Move right by 7 pixels
        double newY = this.paddleShape.getUpperLeft().getY(); // Y coordinate remains the same
        if (newX > gameWidth - 10 - paddleShape.getWidth()) {
            newX = 10; // Set newX to the leftmost position
        }
        Rectangle newPaddle = new Rectangle(new Point(newX, newY),
                this.paddleShape.getWidth(), this.paddleShape.getHeight());
        // Check for collisions with balls
        for (Ball ball : Game.balls) {
            if (newPaddle.contains(ball.getCenter())) {
                return;
            }
        }
        this.paddleShape = newPaddle;
    }

    /**
     * Implements the timePassed method from the Sprite interface.
     * Controls the movement of the paddle based on keyboard input.
     */
    @Override
    public void timePassed() {
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft();
        } else if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
        }
    }


    /**
     * Implements the drawOn method from the Sprite interface.
     * Draws the paddle on the given DrawSurface.
     *
     * @param d The DrawSurface on which to draw the paddle
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.fillRectangle((int) this.paddleShape.getUpperLeft().getX(),
                (int) this.paddleShape.getUpperLeft().getY(),
                (int) this.paddleShape.getWidth(),
                (int) this.paddleShape.getHeight());

        d.setColor(Color.BLACK);
        d.drawRectangle((int) this.paddleShape.getUpperLeft().getX(),
                (int) this.paddleShape.getUpperLeft().getY(),
                (int) this.paddleShape.getWidth(),
                (int) this.paddleShape.getHeight());
    }

    /**
     * Implements the getCollisionRectangle method from the Collidable interface.
     * Returns the collision rectangle (paddle's shape).
     *
     * @return The collision rectangle (paddle's shape)
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return this.paddleShape;
    }

    /**
     * Implements the hit method from the Collidable interface.
     * Handles collisions between the paddle and a ball, changing the ball's velocity accordingly.
     *
     * @param collisionPoint  The point of collision between the ball and the paddle
     * @param currentVelocity The current velocity of the ball
     * @return The new velocity of the ball after collision with the paddle
     */
    @Override
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
        double sectionWidth = this.paddleShape.getWidth() / 5;
        double paddleX = this.paddleShape.getUpperLeft().getX();
        double paddleY = this.paddleShape.getUpperLeft().getY();
        double paddleWidth = this.paddleShape.getWidth();
        double paddleHeight = this.paddleShape.getHeight();
        double hitX = collisionPoint.getX();
        double hitY = collisionPoint.getY();

        // Calculate which section of the paddle was hit
        int section;
        double currentSpeed = Math.sqrt(currentVelocity.getDx() * currentVelocity.getDx()
                + currentVelocity.getDy() * currentVelocity.getDy());

        if (threshold(hitX, paddleX, EPSILON)) {
            return new Velocity(-Math.abs(currentVelocity.getDx()), currentVelocity.getDy());
        }

        if (threshold(hitX, paddleX + paddleWidth, EPSILON)) {
            return new Velocity(Math.abs(currentVelocity.getDx()), currentVelocity.getDy());
        }


        section = (int) Math.round((hitX - paddleX) / sectionWidth);

        switch (section) {
            case 0:
                if (threshold(hitX, paddleX, EPSILON) && threshold(hitY, paddleY, EPSILON)) {
                    return new Velocity(-Math.abs(currentVelocity.getDx()), -Math.abs(currentVelocity.getDy()));
                } else {
                    return Velocity.fromAngleAndSpeed(300, currentSpeed);
                }


            case 1:
                return Velocity.fromAngleAndSpeed(330, currentSpeed);


            case 2:
                if (threshold(paddleY, hitY, EPSILON)) {
                    return new Velocity(currentVelocity.getDx(), -Math.abs(currentVelocity.getDy()));
                }


            case 3:
                return Velocity.fromAngleAndSpeed(30, currentSpeed);


            case 4, 5:

                    return Velocity.fromAngleAndSpeed(60, currentSpeed);


            default:
                return currentVelocity;

        }


    }


    /**
     * Adds this paddle as a sprite and collidable object to the game.
     *
     * @param g The game to which this paddle will be added
     */
    public void addToGame(Game g) {
        g.addSprite(this);
        g.addCollidable(this);
    }
}