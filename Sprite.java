// Name: Yonatan Omer
// ID: 322624693

import biuoop.DrawSurface;

/**
 * The Sprite interface represents objects that can be drawn on a DrawSurface
 * and can update themselves as time passes.
 */
public interface Sprite {
    /**
     * Draws the sprite on a given DrawSurface.
     *
     * @param d The DrawSurface on which to draw the sprite.
     */
    void drawOn(DrawSurface d);

    /**
     * Notifies the sprite that a unit of time has passed.
     * This is used to update the state of the sprite over time.
     */
    void timePassed();
}
