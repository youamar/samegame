package g54915.samegame.model;

import g54915.samegame.view.View;
import org.junit.jupiter.api.Test;
import static g54915.samegame.model.State.*;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void win() {
        Ball[][] board = {
            {new Ball(1)},
            {new Ball(1)},
            {new Ball(1)},
            {new Ball(1)}
        };
        View view = new View();
        Game game = new Game(board, 2);
        game.setState(PLAYING);
        game.subscribe(view);
        game.playShot(new Position(0, 0));
        assertTrue(game.getState() == WIN);
    }

    @Test
    void lost() {
        Ball[][] board = {
            {new Ball(1)},
            {new Ball(1)},
            {new Ball(1)},
            {new Ball(2)}
        };
        View view = new View();
        Game game = new Game(board, 2);
        game.setState(PLAYING);
        game.subscribe(view);
        game.playShot(new Position(0, 0));
        assertTrue(game.getState() == LOST);
    }

    @Test
    void isGameOver() {
        Ball[][] board = {
            {new Ball(3)},
            {new Ball(3)},
            {new Ball(1)},
            {new Ball(1)}
        };
        View view = new View();
        Game game = new Game(board, 2);
        game.setState(PLAYING);
        game.subscribe(view);
        assertFalse(game.isGameOver());
        game.playShot(new Position(0, 0));
        assertFalse(game.isGameOver());
        game.playShot(new Position(0, 2));
        assertEquals(game.getNbBallsRemaining(), 0);
        Ball[][] board2 = {
            {new Ball(3)},
            {new Ball(2)},
            {new Ball(1)},
            {new Ball(1)}
        };
        game = new Game(board, 2);
        game.setState(PLAYING);
        game.subscribe(view);
        assertFalse(game.isGameOver());
    }

    @Test
    void abandon() {
        Ball[][] board = {
            {new Ball(1)},
            {new Ball(1)},
            {new Ball(1)},
            {new Ball(2)}
        };

        View view = new View();
        Game game = new Game(board, 2);
        game.setState(PLAYING);
        game.subscribe(view);
        assertFalse(game.getState() == GAVE_UP);
        game.abandon();
        assertTrue(game.getState() == GAVE_UP);
    }

    /**
     * Test click score 0 et zone
     */
    @Test
    void score() {
        Ball[][] board = {
            {new Ball(1)},
            {new Ball(1)},
            {new Ball(1)},
            {new Ball(2)}
        };
        View view = new View();
        Game game = new Game(board, 2);
        game.setState(PLAYING);
        game.subscribe(view);
        int score = game.playShot(new Position(3, 0));
        assertEquals(score, 0);
        assertTrue(board[3][0].getColor() == 2);
        score = game.playShot(new Position(0, 0));
        assertEquals(score, 3 * 2);
        assertNull(board[0][0]);
        assertNull(board[1][0]);
        assertNull(board[2][0]);
    }

    /**
     * Test chute
     */
    @Test
    void chute() {
        Ball[][] board = {
            {new Ball(1)},
            {new Ball(1)},
            {new Ball(1)},
            {new Ball(2)},
            {new Ball(2)}
        };
        View view = new View();
        Game game = new Game(board, 2);
        game.setState(PLAYING);
        game.subscribe(view);
        int score = game.playShot(new Position(3, 0));
        assertEquals(score, 2);
        assertNull(board[0][0]);
        assertNull(board[1][0]);
        assertNotNull(board[2][0]);
        assertNotNull(board[3][0]);
        assertNotNull(board[4][0]);
    }

    /**
     * Test shift
     */
    @Test
    void shitCol() {
        Ball[][] board = {
            {new Ball(1), new Ball(1), new Ball(3), new Ball(3)},
            {new Ball(1), new Ball(1), new Ball(3), new Ball(3)},
            {new Ball(1), new Ball(1), new Ball(3), new Ball(3)},
            {new Ball(1), new Ball(1), new Ball(3), new Ball(3)},
            {new Ball(1), new Ball(1), new Ball(3), new Ball(3)}
        };
        View view = new View();
        Game game = new Game(board, 2);
        game.setState(PLAYING);
        game.subscribe(view);
        int score = game.playShot(new Position(3, 0));
        assertNotNull(board[0][0]);
        assertNotNull(board[1][0]);
        assertNotNull(board[2][0]);
        assertNotNull(board[3][0]);
        assertNotNull(board[4][0]);
        assertNotNull(board[0][1]);
        assertNotNull(board[1][1]);
        assertNotNull(board[2][1]);
        assertNotNull(board[3][1]);
        assertNotNull(board[4][1]);
        assertNull(board[0][2]);
        assertNull(board[1][2]);
        assertNull(board[2][2]);
        assertNull(board[3][2]);
        assertNull(board[4][2]);
        assertNull(board[0][3]);
        assertNull(board[1][3]);
        assertNull(board[2][3]);
        assertNull(board[3][3]);
        assertNull(board[4][3]);
    }
}
