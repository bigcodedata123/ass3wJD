// Name: Yonatan Omer
// ID: 322624693

import biuoop.DrawSurface;
import java.util.ArrayList;
import java.util.List;

/**
 * The SpriteCollection class represents a collection of sprites.
 * It manages adding sprites, updating them over time, and drawing them on a DrawSurface.
 */
public class SpriteCollection {
    private List<Sprite> sprites;

    /**
     * Constructor to initialize an empty sprite collection.
     */
    public SpriteCollection() {
        this.sprites = new ArrayList<>();
    }

    /**
     * Adds a sprite to the collection.
     *
     * @param s The sprite to add.
     */
    public void addSprite(Sprite s) {
        this.sprites.add(s);
    }

    /**
     * Notifies all sprites in the collection that a unit of time has passed.
     * This method calls timePassed() on each sprite, allowing them to update their state.
     */
    public void notifyAllTimePassed() {
        for (Sprite s : this.sprites) {
            s.timePassed();
        }
    }

    /**
     * Draws all sprites in the collection on a given DrawSurface.
     * This method calls drawOn(d) on each sprite, which draws the sprite on the DrawSurface.
     *
     * @param d The DrawSurface on which to draw the sprites.
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite s : this.sprites) {
            s.drawOn(d);
        }
    }
}
