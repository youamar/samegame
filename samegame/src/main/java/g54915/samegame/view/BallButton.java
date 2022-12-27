package g54915.samegame.view;

import javafx.scene.control.Button;
import javafx.scene.Node;

public class BallButton extends Button {

    private final int x;
    private final int y;

    public BallButton(String text, Node graphic, int x, int y) {
        super(text, graphic);
        this.y = y;
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
