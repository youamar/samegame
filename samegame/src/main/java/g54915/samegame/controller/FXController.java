package g54915.samegame.controller;

import g54915.samegame.command.Command;
import g54915.samegame.command.PlayCommand;
import g54915.samegame.model.Ball;
import g54915.samegame.view.BallButton;
import g54915.samegame.model.Facade;
import g54915.samegame.model.Position;
import g54915.samegame.view.AbstractView;
import g54915.samegame.view.ViewFX;
import javafx.event.ActionEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class FXController extends Controller {

    private final Stage stage;

    public FXController(Facade facade, AbstractView view, Stage stage) {
        super(facade, view);
        this.stage = stage;
    }

    public void updateBoard(Ball[][] s, GridPane gridPane) {
        for (int i = 0; i < s.length; i++) { //repopulates board
            for (int j = 0; j < s[i].length; j++) {
                BallButton ballBt = new BallButton("", new Circle(10, ((ViewFX) (getView())).getColor(s[i][j])), i, j);
                gridPane.add(ballBt, j, i);
                ballBt.prefHeightProperty().bind(gridPane.heightProperty());
                ballBt.prefWidthProperty().bind(gridPane.widthProperty());
                ballBt.setStyle("-fx-background-color: #000000;");
                ballBt.setOnMouseEntered(e -> {
                    ((ViewFX) getView()).highLight(getFacade().getZone(new Position(ballBt.getX(), ballBt.getY())));
                });
                ballBt.setOnAction((ActionEvent event) -> {
                    playShot(new Position(ballBt.getX(), ballBt.getY()));
                });
            }
        }
    }

    private void playShot(Position position) {
        Command command = new PlayCommand(position);
        getFacade().execute(command);
    }

    @Override
    public void run() {
        getFacade().initialize();

    }
}
