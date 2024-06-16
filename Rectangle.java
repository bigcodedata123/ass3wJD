// Name: Yonatan Omer
// ID: 322624693

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a rectangle in a 2D coordinate system.
 */
public class Rectangle {
    private final Point upperLeft; // The upper-left corner of the rectangle
    private final double width; // The width of the rectangle
    private final double height; // The height of the rectangle
    private final double epsilon = Line.EPSILON; // Threshold for double comparison

    /**
     * Constructor to create a rectangle with given upper-left corner, width, and height.
     *
     * @param upperLeft The upper-left corner of the rectangle.
     * @param width     The width of the rectangle.
     * @param height    The height of the rectangle.
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }

    /**
     * Method to find intersection points of this rectangle with a given line.
     *
     * @param line The line to check for intersections with the rectangle.
     * @return A list of intersection points with the line.
     */
    public List<Point> intersectionPoints(Line line) {
        List<Point> intersectionPoints = new ArrayList<>();
        List<Line> edges = getEdges();

        for (Line edge : edges) {
            Point intersection = edge.intersectionWith(line);
            if (intersection != null) {
                intersectionPoints.add(intersection);
            }
        }

        return intersectionPoints;
    }

    /**
     * Helper method to get the edges of the rectangle as lines.
     *
     * @return A list of lines representing the edges of the rectangle.
     */
    private List<Line> getEdges() {
        double x1 = upperLeft.getX();
        double y1 = upperLeft.getY();
        double x2 = upperLeft.getX() + width;
        double y2 = upperLeft.getY() + height;

        List<Line> edges = new ArrayList<>();
        edges.add(new Line(x1, y1, x2, y1)); // Top edge
        edges.add(new Line(x1, y2, x2, y2)); // Bottom edge
        edges.add(new Line(x1, y1, x1, y2)); // Left edge
        edges.add(new Line(x2, y1, x2, y2)); // Right edge

        return edges;
    }

    /**
     * Method to check if a given point is contained within this rectangle.
     *
     * @param point The point to check.
     * @return True if the point is inside or on the edge of the rectangle, false otherwise.
     */
    public boolean contains(Point point) {
        double px = point.getX();
        double py = point.getY();
        double rx = this.getUpperLeft().getX();
        double ry = this.getUpperLeft().getY();

        boolean insideXBounds = (px > rx) && (px < rx + this.width);
        boolean insideYBounds = (py > ry) && (py < ry + this.height);

        boolean onEdgeXBounds = threshold(px, rx, epsilon) || threshold(px, rx + this.width, epsilon);
        boolean onEdgeYBounds = threshold(py, ry, epsilon) || threshold(py, ry + this.height, epsilon);

        return (insideXBounds && insideYBounds) || (onEdgeXBounds && onEdgeYBounds);
    }

    /**
     * Getter method to return the width of this rectangle.
     *
     * @return The width of this rectangle.
     */
    public double getWidth() {
        return width;
    }

    /**
     * Getter method to return the height of this rectangle.
     *
     * @return The height of this rectangle.
     */
    public double getHeight() {
        return height;
    }

    /**
     * Getter method to return the upper-left corner of this rectangle.
     *
     * @return The upper-left corner of this rectangle.
     */
    public Point getUpperLeft() {
        return upperLeft;
    }

    /**
     * Helper method to compare two doubles with a threshold for equality.
     *
     * @param a       The first double to compare.
     * @param b       The second double to compare.
     * @param epsilon The precision threshold.
     * @return True if the doubles are considered equal within the threshold, false otherwise.
     */
    boolean threshold(double a, double b, double epsilon) {
        return Math.abs(a - b) < epsilon;
    }
}
