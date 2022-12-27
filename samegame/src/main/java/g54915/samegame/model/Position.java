package g54915.samegame.model;

/**
 * This class represents a position in SameGame.
 */
public class Position {

    private int x;
    private int y;

    /**
     * Simple constructor of a Position.
     *
     * @param x the x coordinate.
     * @param y the y coordinate.
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Position{"
                + "x=" + x
                + ", y=" + y
                + '}';
    }

    /**
     * Simple getter of the x coordinate.
     *
     * @return the x coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Simple getter of the y coordinate.
     *
     * @return the y coordinate.
     */
    public int getY() {
        return y;
    }

    /**
     * Simple setter of the x coordinate.
     *
     * @param x the x coordinate.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Simple setter of the y coordinate.
     *
     * @param y the y coordinate.
     */
    public void setY(int y) {
        this.y = y;
    }

}
