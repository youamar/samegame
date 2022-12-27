package g54915.samegame.model;

import g54915.samegame.command.Command;
import g54915.samegame.view.AbstractView;

import java.util.ArrayList;
import java.util.Stack;

/**
 * This class represents a Facade of SameGame.
 */
public class Facade {

    private Game game;
    private final Stack<Command> commandDone;
    private final Stack<Command> commandUndo;

    /**
     * Simple constructor of a Facade.
     *
     * @param game the game SameGame.
     */
    public Facade(Game game) {
        this.game = game;
        commandDone = new Stack<>();
        commandUndo = new Stack<>();
    }

    /**
     * This method initializes the game based on the values
     * defined in the game.
     */
    public void startGame() {
        game.initialize();
    }

    /**
     * This method allows you to play a possible shot with a given position
     * and deletes the zone of the shot.
     *
     * @param pos a given position.
     */
    public int playShot(Position pos) {
        if (!game.isGameOver()) {
            return game.playShot(pos);
        }
        return 0;
    }

    /**
     * This method executes a given command.
     *
     * @param com a given command.
     */
    public void execute(Command com) {
        if (com != null) {
            com.setFacade(this);
            com.execute();
        }
    }

    /**
     * This method adds a given command to the list of done commands.
     *
     * @param command a given command.
     */
    public void addCommand(Command command) {
        commandDone.push(command);
    }

    /**
     * This method pops a command from the non-empty list of done commands.
     */
    public Command popLastCommand() {
        return commandDone.isEmpty() ? null : commandDone.pop();
    }

    /**
     * This method pops a command from the non-empty list of undone commands.
     */
    public Command popLastCommandUndo() {
        return commandUndo.isEmpty() ? null : commandUndo.pop();
    }

    /**
     * This method adds a given command to the list of undone commands.
     *
     * @param command a given command.
     */
    public void addUndoCommand(Command command) {
        commandUndo.push(command);
    }

    /**
     * This method allows you to give up a game of SameGame.
     */
    public void abandon() {
        game.abandon();
        startGame();
    }

    /**
     * This method initializes the game based on the values
     * defined in the game.
     */
    public void initialize() {
        game.initialize();
    }

    public void subscribe(AbstractView ob) {
        game.subscribe(ob);
    }

    public State getState() {
        return game.getState();
    }

    public ArrayList<Position> getZone(Position position) {
        return game.getZone(position);
    }

    public void deleteBiggestZone() {
       game.deleteBiggestZone();
    }

    public Game getCopyGame() {
        return new Game(getBoardCopy(), game.getSupport(), game.getState(), game.getNbColors(), game.getScore(), game.getClickScore());
    }

    public Ball[][] getBoardCopy() {
        Ball[][] boardCopy = new Ball[game.getBoardWidth()][game.getBoardHeight()];
        for (int i = 0; i < game.getBoardWidth(); i++) {
            for (int j = 0; j < game.getBoardHeight(); j++) {
                Ball ballCopy = game.getBall(new Position(i, j));
                if (ballCopy != null) {
                    boardCopy[i][j] = new Ball(ballCopy.getColor());
                }
            }
        }
        return boardCopy;
    }

    public void setNewGame(int width, int height, int nbColors) {
        game.updateBoard(width, height, nbColors);
        startGame();
    }

    public void setNewGame(Game prevGame) {
        this.game = prevGame;
        game.notifyView();
    }
}
