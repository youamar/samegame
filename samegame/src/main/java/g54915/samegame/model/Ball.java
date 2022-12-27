package g54915.samegame.model;

import java.util.Objects;

/**
 * This class represents a ball in SameGame.
 */
public class Ball {

    private int color;

    /**
     * Simple constructor of a Ball.
     *
     * @param color the color of the Ball.
     */
    public Ball(int color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ball ball = (Ball) o;
        return color == ball.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color);
    }

    @Override
    public String toString() {
        return "Ball{"
                + "color=" + color
                + '}';
    }

    /**
     * Simple getter of a Ball's color.
     *
     * @return the color of the Ball.
     */
    public int getColor() {
        return color;
    }

}
