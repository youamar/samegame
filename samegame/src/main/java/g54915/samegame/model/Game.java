package g54915.samegame.model;

import g54915.samegame.view.AbstractView;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Game {

    private Ball[][] board;
    private boolean[][] sameColors;
    private final PropertyChangeSupport support;
    private State state = State.GAVE_UP;
    private int nbColors;
    private int score = 0;
    private int clickScore = 0;

    /**
     * Complete constructor to create a game copy from.
     *
     * @param board      the Balls board.
     * @param support    the game support.
     * @param state      the game state.
     * @param nbColors   the level or number of colors.
     * @param score      the total score.
     * @param clickScore the last click score.
     */
    public Game(Ball[][] board, PropertyChangeSupport support, State state, int nbColors, int score, int clickScore) {
        this.board = board;
        this.support = support;
        this.state = state;
        this.nbColors = nbColors;
        this.score = score;
        this.clickScore = clickScore;
    }

    /**
     * Simple constructor of SameGame.
     *
     * @param board    the Balls board.
     * @param nbColors the level of the game (or number of colors).
     */
    public Game(Ball[][] board, int nbColors) {
        this.board = board;
        this.nbColors = nbColors;
        support = new PropertyChangeSupport(this);
    }

    /**
     * Simple constructor of SameGame.
     *
     * @param nbColors the level of the game (or number of colors).
     */
    public Game(int nbColors) {
        this.board = new Ball[12][16];
        this.nbColors = nbColors;
        support = new PropertyChangeSupport(this);
    }

    /**
     * Simple constructor of SameGame.
     *
     * @param board the Balls board.
     */
    public Game(Ball[][] board) {
        this.board = board;
        this.nbColors = 3;
        support = new PropertyChangeSupport(this);
    }

    /**
     * Simple constructor of SameGame.
     */
    public Game() {
        this.board = new Ball[12][16];
        this.nbColors = 3;
        support = new PropertyChangeSupport(this);
    }

    /**
     * This method initializes the game based on the values
     * defined in the game.
     */
    public void initialize() {
        Random random = new Random();
        for (int i = 0; i < getBoardWidth(); i++) {
            for (int j = 0; j < getBoardHeight(); j++) {
                board[i][j] = new Ball(random.nextInt(nbColors));
            }
        }
        state = State.PLAYING;
        score = 0;
        notifyView();
    }

    /**
     * This method allows you to play a possible shot with a given position
     * and deletes the zone of the shot.
     *
     * @param pos a given position.
     * @return the score of the played shot.
     */
    public int playShot(Position pos) {
        int score = 0;
        if (getState() == State.PLAYING && pos != null && verifyPos(pos) && getBall(pos) != null) {
            sameColors = new boolean[getBoardWidth()][getBoardHeight()];
            score += playShot(pos, getBall(pos).getColor());
            score = score * (score - 1);
            this.score += score;
            if (score > 0) {
                clickScore = score;
                removeBall();
                applyRule();
            }
            notifyView();
            updateState();
            if (score == 0) {
                support.firePropertyChange("impossible", null, this.clickScore);

            }
        } else {
            notifyView();
            support.firePropertyChange("impossible", null, this.clickScore);

        }
        return score;
    }

    /**
     * This method calculates the number of remaining balls.
     *
     * @return the number of remaining balls.
     */
    private int nbLeftBall() {
        int nbRemainingBalls = 0;
        for (int i = 0; i < getBoardWidth(); i++) {
            for (int j = 0; j < getBoardHeight(); j++) {
                if (board[i][j] != null) {
                    nbRemainingBalls++;
                }
            }
        }
        return nbRemainingBalls;
    }

    /**
     * This method verifies if the given position is well bounded.
     *
     * @param pos a given position
     * @return true if the given position is well bounded and false otherwise.
     */
    private boolean verifyPos(Position pos) {
        return 0 <= pos.getX() && pos.getX() < getBoardWidth() && 0 <= pos.getY() && pos.getY() < getBoardHeight();
    }

    /**
     * This method notifies the view.
     */
    public void notifyView() {
        notifyChangeBoard();
        support.firePropertyChange("leftBall", null, nbLeftBall());
        support.firePropertyChange("clscore", null, this.clickScore);
        support.firePropertyChange("score", null, this.score);
        support.firePropertyChange("state", null, this.state);
    }

    /**
     * This method updates the state based on the current game state.
     */
    private void updateState() {
        if (checkFinish()) {
            if (checkWin()) {
                state = State.WIN;

            } else {
                state = State.LOST;
            }
            support.firePropertyChange("state", null, this.state);
        }
    }

    /**
     * This method verifies if the player won, this method is
     * only called if the game is over.
     *
     * @return true if the player won and false otherwise.
     */
    private boolean checkWin() {
        for (int i = 0; i < getBoardWidth(); i++) {
            for (int j = 0; j < getBoardHeight(); j++) {
                if (board[i][j] != null) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * This method applies all rules of SameGame.
     */
    private void applyRule() {
        fall();
        shiftColumn();
    }

    /**
     * This method shifts all empty columns to the right.
     */
    private void shiftColumn() {
        for (int j = getBoardHeight() - 2; j >= 0; j--) {
            boolean isColNull = true;
            for (int i = 0; i < getBoardWidth(); i++) {
                isColNull = isColNull && board[i][j] == null;
            }
            if (isColNull) {
                shiftLeft(j);
            }
        }
    }

    /**
     * This method applies a shift to the left based on a given index.
     *
     * @param index a given index.
     */
    private void shiftLeft(int index) {
        for (int j = index; j < getBoardHeight() - 1; j++) {
            for (int i = 0; i < getBoardWidth(); i++) {
                board[i][j] = board[i][j + 1];
                board[i][j + 1] = null;
            }
        }
    }

    /**
     * This method applies gravity on each row.
     */
    private void fall() {
        for (int j = 0; j < getBoardHeight(); j++) {
            Position fl = null;
            for (int i = getBoardWidth() - 1; i >= 0; i--) {
                if (board[i][j] == null && fl == null) {
                    fl = new Position(i, j);
                }
                if (board[i][j] != null && fl != null) {
                    board[fl.getX()][fl.getY()] = board[i][j];
                    board[i][j] = null;
                    fl.setX(fl.getX() - 1);
                }

            }
        }
    }

    /**
     * This method deletes the balls of a zone.
     */
    private void removeBall() {
        for (int i = 0; i < getBoardWidth(); i++) {
            for (int j = 0; j < getBoardHeight(); j++) {
                if (sameColors[i][j]) {
                    board[i][j] = null;
                }
            }
        }
    }

    /**
     * This method allows you to calculate the score and the zone of a given position.
     *
     * @param pos   a given pos.
     * @param color a given color.
     * @return the score.
     */
    public int playShot(Position pos, int color) {
        int score = 0;

        if (0 <= pos.getX() && pos.getX() < getBoardWidth() && 0 <= pos.getY() && pos.getY() < getBoardHeight()) {
            if (getBall(pos) != null && getBall(pos).getColor() == color && !sameColors[pos.getX()][pos.getY()]) {
                score++;
                sameColors[pos.getX()][pos.getY()] = true;
                score += playShot(new Position(pos.getX() + 1, pos.getY()), color);
                score += playShot(new Position(pos.getX() - 1, pos.getY()), color);
                score += playShot(new Position(pos.getX(), pos.getY() - 1), color);
                score += playShot(new Position(pos.getX(), pos.getY() + 1), color);

            }
        }
        return score;
    }

    public boolean isGameOver() {
        return state != State.PLAYING;
    }

    /**
     * This method checks if the game is finished or not.
     *
     * @return true if the game is finished and false otherwise.
     */
    private boolean checkFinish() {
        for (int i = 0; i < getBoardWidth(); i++) {
            for (int j = 0; j < getBoardHeight(); j++) {
                if (board[i][j] != null && (((j + 1 < getBoardHeight() - 1 && Objects.equals(board[i][j], board[i][j + 1]))
                        || (i + 1 < getBoardWidth() - 1 && Objects.equals(board[i][j], board[i + 1][j]))
                        || (j - 1 >= 0 && Objects.equals(board[i][j], board[i][j - 1]))
                        || (i - 1 >= 0 && Objects.equals(board[i][j], board[i - 1][j]))))) {
                    return false;
                }

            }
        }
        return true;
    }

    /**
     * This method updates the board.
     *
     * @param width    the board's width.
     * @param height   the board's height.
     * @param nbColors the board's number of colors.
     */
    public void updateBoard(int width, int height, int nbColors) {
        setBoard(width, height);
        this.clickScore = 0;
        this.score = 0;
        this.nbColors = nbColors;
    }

    /**
     * This method allows you to give up a game of SameGame.
     */
    public void abandon() {
        state = State.GAVE_UP;
        support.firePropertyChange("state", null, this.state);
    }

    /**
     * This method gives you a zone of same colored balls.
     *
     * @param position a given position.
     * @return a zone of same colored balls.
     */
    public ArrayList<Position> getZone(Position position) {
        ArrayList<Position> res = new ArrayList<>();
        sameColors = new boolean[getBoardWidth()][getBoardHeight()];
        addZone(res, position, (getBall(position) == null ? 1 : getBall(position).getColor()));
        return res;
    }

    public void deleteBiggestZone() {
        ArrayList<Position> zoneList = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (zoneList.size() <= getZone(new Position(i, j)).size()) {
                    zoneList = getZone(new Position(i, j));
                }
            }
        }
        playShot(zoneList.get(0));
    }

    /**
     * This method calculates a zone and adds its positions in an arraylist.
     *
     * @param res   a positions array.
     * @param pos   a given position.
     * @param color a given color.
     */
    private void addZone(ArrayList<Position> res, Position pos, int color) {
        if (0 <= pos.getX() && pos.getX() < getBoardWidth() && 0 <= pos.getY() && pos.getY() < getBoardHeight()) {
            if (getBall(pos) != null && getBall(pos).getColor() == color && !sameColors[pos.getX()][pos.getY()]) {
                res.add(pos);
                sameColors[pos.getX()][pos.getY()] = true;
                addZone(res, new Position(pos.getX() + 1, pos.getY()), color);
                addZone(res, new Position(pos.getX() - 1, pos.getY()), color);
                addZone(res, new Position(pos.getX(), pos.getY() - 1), color);
                addZone(res, new Position(pos.getX(), pos.getY() + 1), color);

            }
        }
    }

    public int getScore() {
        return score;
    }

    public Ball getBall(Position pos) {
        return pos == null ? null : board[pos.getX()][pos.getY()];
    }

    public int getNbColors() {
        return nbColors;
    }

    public int getBoardWidth() {
        return board.length;
    }

    public int getBoardHeight() {
        return board[0].length;
    }

    public State getState() {
        return state;
    }

    public int getClickScore() {
        return clickScore;
    }

    public int getNbBallsRemaining() {
        return 0;
    }

    public void setBoard(int width, int height) {
        if (width > 1 && height > 1) {
            this.board = new Ball[width][height];
        }
    }

    public void setState(State o) {
        state = o;
    }

    public PropertyChangeSupport getSupport() {
        return support;
    }

    public void subscribe(AbstractView ob) {
        support.addPropertyChangeListener(ob);
    }

    public void unsubscribe(AbstractView ob) {
        support.removePropertyChangeListener(ob);
    }

    private void notifyChangeBoard() {
        support.firePropertyChange("board", null, this.board);
    }
}
