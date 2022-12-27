package g54915.samegame.main;

import g54915.samegame.controller.FXController;
import g54915.samegame.model.Ball;
import g54915.samegame.view.ViewFX;
import javafx.application.Application;
import javafx.stage.Stage;
import g54915.samegame.model.Game;
import g54915.samegame.model.Facade;

public class SameGameFX extends Application {

    @Override
    public void start(Stage stage) {
        var board = new Ball[12][16];
        var difficulty = 3;
        var game = new Game(board, difficulty);
        var facade = new Facade(game);
        var controller = new FXController(facade, new ViewFX(stage), stage);
        controller.run();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
