/**
 * A class that represents a path via pursuit curves.
 * @author You!
 */
public class Path {

    public Point curr, next;

    public Path(double x, double y) {
        curr = new Point(50, 50);
        next = new Point(x, y);
    }

    public void iterate(double dx, double dy) {
        curr = next;
        next.x = curr.x + dx;
        next.y = curr.y + dy;
    }

    public double getCurrX() {
        return curr.x;
    }

    public double getCurrY() {
        return curr.y;
    }

    public double getNextX() {
        return next.x;
    }

    public double getNextY() {
        return next.y;
    }
}
