// Name: Yonatan Omer
// ID: 322624693

/**
 * A Collidable interface represents an object that can collide with other objects in the game.
 */
public interface Collidable {
    /**
     * Returns the collision shape of the object.
     *
     * @return The collision rectangle representing the shape of the object.
     */
    Rectangle getCollisionRectangle();

    /**
     * Notifies the object that a collision occurred at a specific collision point with a given velocity.
     * Returns the new velocity expected after the hit, based on the force inflicted by the object.
     *
     * @param collisionPoint The point where the collision occurred.
     * @param currentVelocity The current velocity of the colliding object.
     * @return The new velocity of the colliding object after the collision.
     */
    Velocity hit(Point collisionPoint, Velocity currentVelocity);
}
