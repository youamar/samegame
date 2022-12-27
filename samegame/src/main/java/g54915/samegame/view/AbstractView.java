package g54915.samegame.view;

import g54915.samegame.command.Command;
import g54915.samegame.controller.Controller;
import g54915.samegame.model.Ball;
import g54915.samegame.model.Position;
import g54915.samegame.model.State;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public interface AbstractView extends PropertyChangeListener {

    @Override
    default void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "score":
                updateScore((int) evt.getNewValue());
                break;
            case "board":
                updateBoard((Ball[][]) evt.getNewValue());
                break;
            case "impossible":
                impossibleMove();
                break;
            case "clscore":
                updateClickScore((int) evt.getNewValue());
                break;
            case "state":
                State state = (State) evt.getNewValue();
                showEndGameMessage(state);
                break;
            case "leftBall":
                updateLeftBall((int) evt.getNewValue());
                break;

        }
    }

    void updateLeftBall(int newValue);

    void showEndGameMessage(State state);

    void impossibleMove();

    void updateClickScore(int newValue);

    void updateScore(int newValue);

    void updateBoard(Ball[][] newValue);

    Position getPosition();

    Command getCommand();

    void setController(Controller controller);
}
