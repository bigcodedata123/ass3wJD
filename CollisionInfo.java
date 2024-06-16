// Name: Yonatan Omer
// ID: 322624693

/**
 * Represents information about a collision, including the collision point and the collidable object involved.
 */
public class CollisionInfo {
    // The point at which the collision occurs
    private Point collisionPoint;
    // The collidable object involved in the collision
    private Collidable collisionObject;

    /**
     * Constructor to initialize the collision information.
     *
     * @param collisionPoint   The point at which the collision occurs
     * @param collisionObject  The collidable object involved in the collision
     */
    public CollisionInfo(Point collisionPoint, Collidable collisionObject) {
        // Initialize the collision point
        this.collisionPoint = collisionPoint;
        // Initialize the collidable object
        this.collisionObject = collisionObject;
    }

    /**
     * Get the collision point.
     *
     * @return The point at which the collision occurs
     */
    public Point collisionPoint() {
        // Return the collision point
        return collisionPoint;
    }

    /**
     * Get the collidable object involved in the collision.
     *
     * @return The collidable object
     */
    public Collidable collisionObject() {
        // Return the collidable object
        return collisionObject;
    }
}
