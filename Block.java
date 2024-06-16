// Name: Yonatan Omer
// ID: 322624693

import biuoop.DrawSurface;
import java.awt.Color;

/**
 * Represents a block in the game, which can collide with other objects and be drawn on the screen.
 */
public class Block implements Collidable, Sprite {
    // The rectangle that defines the block's collision area
    private final Rectangle collisionRectangle;
    // The color of the block
    private final Color color;

    /**
     * Constructor to initialize the block.
     *
     * @param collisionRectangle The rectangle defining the block's collision area
     * @param color              The color of the block
     */
    public Block(Rectangle collisionRectangle, Color color) {
        // Initialize the collision rectangle
        this.collisionRectangle = collisionRectangle;
        // Initialize the color
        this.color = color;
    }

    /**
     * Add the block to the game.
     *
     * @param game The game to add the block to
     */
    public void addToGame(Game game) {
        // Add the block as a collidable object
        game.addCollidable(this);
        // Add the block as a sprite to be drawn
        game.addSprite(this);
    }

    /**
     * Draw the block on the given DrawSurface.
     *
     * @param surface The DrawSurface on which to draw the block
     */
    @Override
    public void drawOn(DrawSurface surface) {
        // Set the color of the block
        surface.setColor(this.color);
        // Fill the rectangle representing the block
        surface.fillRectangle(
                (int) this.collisionRectangle.getUpperLeft().getX(),
                (int) this.collisionRectangle.getUpperLeft().getY(),
                (int) this.collisionRectangle.getWidth(),
                (int) this.collisionRectangle.getHeight()
        );

        // Set the color for the border of the block
        surface.setColor(Color.BLACK);
        // Draw the rectangle representing the block's border
        surface.drawRectangle(
                (int) this.collisionRectangle.getUpperLeft().getX(),
                (int) this.collisionRectangle.getUpperLeft().getY(),
                (int) this.collisionRectangle.getWidth(),
                (int) this.collisionRectangle.getHeight()
        );
    }

    /**
     * Notify the block that a unit of time has passed.
     */
    @Override
    public void timePassed() {
        // No implementation needed for this example
    }

    /**
     * Helper method to compare doubles with precision.
     *
     * @param a       The first double to compare
     * @param b       The second double to compare
     * @param epsilon The precision threshold
     * @return True if the difference between a and b is less than epsilon, false otherwise
     */
    boolean threshold(double a, double b, double epsilon) {
        // Compare the absolute difference between a and b with epsilon
        return Math.abs(a - b) < epsilon;
    }

    /**
     * Get the collision rectangle of the block.
     *
     * @return The collision rectangle
     */
    @Override
    public Rectangle getCollisionRectangle() {
        // Return the collision rectangle
        return this.collisionRectangle;
    }

    /**
     * Notify the block of a collision and return the new velocity after the collision.
     *
     * @param collisionPoint   The point of collision
     * @param currentVelocity  The current velocity before the collision
     * @return The new velocity after the collision
     */
    @Override
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
        // Get the x-coordinate of the collision point
        double x = collisionPoint.getX();
        // Get the y-coordinate of the collision point
        double y = collisionPoint.getY();
        // Get the x-coordinate of the upper-left corner of the collision rectangle
        double rectX = collisionRectangle.getUpperLeft().getX();
        // Get the y-coordinate of the upper-left corner of the collision rectangle
        double rectY = collisionRectangle.getUpperLeft().getY();
        // Get the width of the collision rectangle
        double rectWidth = collisionRectangle.getWidth();
        // Get the height of the collision rectangle
        double rectHeight = collisionRectangle.getHeight();
        // Define the precision threshold
        double epsilon = Line.EPSILON;

        // Check for collision with the upper-left corner
        if (threshold(x, rectX, epsilon) && threshold(y, rectY, epsilon)) {
            // Return the new velocity after collision
            return new Velocity(-Math.abs(currentVelocity.getDx()), -Math.abs(currentVelocity.getDy()));
        }

        // Check for collision with the upper-right corner
        if (threshold(x, rectX + rectWidth, epsilon) && threshold(y, rectY, epsilon)) {
            // Return the new velocity after collision
            return new Velocity(Math.abs(currentVelocity.getDx()), -Math.abs(currentVelocity.getDy()));
        }

        // Check for collision with the lower-left corner
        if (threshold(x, rectX, epsilon) && threshold(y, rectY + rectHeight, epsilon)) {
            // Return the new velocity after collision
            return new Velocity(-Math.abs(currentVelocity.getDx()), Math.abs(currentVelocity.getDy()));
        }

        // Check for collision with the lower-right corner
        if (threshold(x, rectX + rectWidth, epsilon) && threshold(y, rectY + rectHeight, epsilon)) {
            // Return the new velocity after collision
            return new Velocity(Math.abs(currentVelocity.getDx()), Math.abs(currentVelocity.getDy()));
        }

        // Check for collision with the upper edge
        if (threshold(y, rectY, epsilon)) {
            // Return the new velocity after collision
            return new Velocity(currentVelocity.getDx(), -Math.abs(currentVelocity.getDy()));
        }

        // Check for collision with the lower edge
        if (threshold(y, rectY + rectHeight, epsilon)) {
            // Return the new velocity after collision
            return new Velocity(currentVelocity.getDx(), Math.abs(currentVelocity.getDy()));
        }

        // Check for collision with the left edge
        if (threshold(x, rectX, epsilon)) {
            // Return the new velocity after collision
            return new Velocity(-Math.abs(currentVelocity.getDx()), currentVelocity.getDy());
        }

        // Check for collision with the right edge
        if (threshold(x, rectX + rectWidth, epsilon)) {
            // Return the new velocity after collision
            return new Velocity(Math.abs(currentVelocity.getDx()), currentVelocity.getDy());
        }

        // Return the current velocity if no collision is detected
        return currentVelocity;
    }
}
