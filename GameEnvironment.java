// Name: Yonatan Omer
// ID: 322624693

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the game environment that holds all collidable objects and manages collisions.
 */
public class GameEnvironment {
    // List of collidable objects in the environment
    private List<Collidable> collidables;
    // Width of the game screen
    private final int screenWidth;
    // Height of the game screen
    private final int screenHeight;

    /**
     * Constructor to initialize the game environment with screen dimensions.
     *
     * @param screenWidth  The width of the game screen
     * @param screenHeight The height of the game screen
     */
    public GameEnvironment(int screenWidth, int screenHeight) {
        // Initialize the screen width
        this.screenWidth = screenWidth;
        // Initialize the screen height
        this.screenHeight = screenHeight;
        // Initialize the list of collidables
        this.collidables = new ArrayList<>();
    }

    /**
     * Get the list of collidable objects.
     *
     * @return The list of collidables
     */
    public List<Collidable> getCollidables() {
        // Return the list of collidables
        return this.collidables;
    }

    /**
     * Get the width of the game screen.
     *
     * @return The screen width
     */
    public int getScreenWidth() {
        // Return the screen width
        return this.screenWidth;
    }

    /**
     * Get the height of the game screen.
     *
     * @return The screen height
     */
    public int getScreenHeight() {
        // Return the screen height
        return this.screenHeight;
    }

    /**
     * Add the given collidable to the environment.
     *
     * @param c The collidable to add
     */
    public void addCollidable(Collidable c) {
        // Add the collidable to the list
        collidables.add(c);
    }

    /**
     * Get the information about the closest collision that is going to occur.
     *
     * @param trajectory The trajectory line of the moving object
     * @return The collision information of the closest collision, or null if no collision occurs
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        // Initialize the closest collision info
        CollisionInfo closestCollision = null;
        // Initialize the closest distance with the maximum value
        double closestDistance = Double.MAX_VALUE;
        // Loop through each collidable object
        for (Collidable c : collidables) {
            // Get the closest intersection point with the current collidable
            Point intersect = trajectory.closestIntersectionToStartOfLine(c.getCollisionRectangle());
            // Check if there is an intersection
            if (intersect != null) {
                // Calculate the distance from the start of the trajectory to the intersection point
                double distance = trajectory.start().distance(intersect);
                // Check if this is the closest intersection found so far
                if (distance < closestDistance) {
                    // Update the closest distance
                    closestDistance = distance;
                    // Update the closest collision info
                    closestCollision = new CollisionInfo(intersect, c);
                }
            }
        }
        // Return the closest collision info
        return closestCollision;
    }
}
