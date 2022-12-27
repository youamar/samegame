package g54915.samegame.main;

import g54915.samegame.controller.Controller;
import g54915.samegame.model.Ball;
import g54915.samegame.model.Facade;
import g54915.samegame.model.Game;
import g54915.samegame.view.View;

public class SameGame {

    public static void main(String[] args) {
        var board = new Ball[12][16];
        var difficulty = 3;
        var game = new Game(board, difficulty);
        var facade = new Facade(game);
        var controller = new Controller(facade, new View());
        controller.run();
    }
}
