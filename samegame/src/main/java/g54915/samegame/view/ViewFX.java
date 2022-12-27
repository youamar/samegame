package g54915.samegame.view;

import g54915.samegame.command.*;
import g54915.samegame.controller.Controller;
import g54915.samegame.controller.FXController;
import g54915.samegame.model.Ball;
import g54915.samegame.model.Position;
import g54915.samegame.model.State;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

public class ViewFX implements AbstractView {

    private GridPane gridPane;
    private ComboBox<Level> cb;
    private final Stage primaryStage;
    private BorderPane borderPane;
    private FXController controller;
    private VBox bottomVb, arrowsVb, infoVb;
    private Button newGameBt, undoBt, redoBt, settingsBt, deleteZone;
    private HBox scoreHb, settingsHb, gameTitleHb, arrowsHb;
    private final PauseTransition notification = new PauseTransition(Duration.seconds(1));
    private Label scLb, csLb, levelLb, titleLb, clickScoreLb, scoreLb, ballsLb, infoLb, infLb, remainingLb;
    private final Color[] ballColor = {Color.BLACK, Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.PURPLE};
    private ArrayList<Position> highlightBtnPos = new ArrayList<>();
    private TextFieldNumberBounded heightTf;
    private TextFieldNumberBounded widthtTf;

    public ViewFX(Stage stage) {
        this.primaryStage = stage;
        initializeContainers();
        initializeLabels();
        initializeButtons();
        setSettingsButtonAction();
        arrowsVb.getChildren().add(arrowsHb);
        scoreHb.getChildren().addAll(scLb, scoreLb, csLb, clickScoreLb);
        settingsHb.getChildren().addAll(deleteZone, newGameBt, levelLb, cb, settingsBt);
        arrowsHb.getChildren().addAll(undoBt, redoBt);
        gameTitleHb.getChildren().add(titleLb);
        bottomVb.getChildren().addAll(scoreHb, settingsHb);
        infoVb.getChildren().addAll(ballsLb, remainingLb, infoLb, infLb);
        borderPane.setTop(gameTitleHb);
        borderPane.setCenter(gridPane);
        borderPane.setBottom(bottomVb);
        borderPane.setLeft(arrowsVb);
        borderPane.setRight(infoVb);
        borderPane.getBottom().minHeight(100);
        showStage(stage);
    }

    private void setSettingsButtonAction() {
        settingsBt.setOnAction((ActionEvent event) -> {
            BorderPane settingsBp = new BorderPane();
            VBox sizeVb = new VBox();
            sizeVb.setPadding(new Insets(10, 0, 0, 10));
            HBox confirmationHb = new HBox();
            settingsBp.setCenter(sizeVb);
            sizeVb.setSpacing(10);
            Label heightLb = new Label("Board height:");
            Label widthLb = new Label("Board width:");
            Button saveBt = new Button("SAVE");
            Button cancelBt = new Button("CANCEL");
            confirmationHb.getChildren().addAll(saveBt, cancelBt);
            sizeVb.getChildren().addAll(heightLb, heightTf, widthLb, widthtTf, confirmationHb);
            confirmationHb.setSpacing(145);
            Scene settingsScene = new Scene(settingsBp, 270, 200);
            // New window (Stage)
            Stage newSettingsWindow = new Stage();
            cancelBt.setOnAction(actionEvent -> {
                heightTf.setText("");
                widthtTf.setText("");
                newSettingsWindow.close();
            });
            saveBt.setOnAction(e -> {
                newSettingsWindow.close();
                executeNewGame();
            });
            newSettingsWindow.setTitle("Settings");
            newSettingsWindow.setScene(settingsScene);
            newSettingsWindow.setMinWidth(286);
            newSettingsWindow.setMinHeight(239);
            newSettingsWindow.setMaxWidth(286);
            newSettingsWindow.setMaxHeight(239);
            // Specifies the modality for new window.
            newSettingsWindow.initModality(Modality.WINDOW_MODAL);
            // Specifies the owner Window (parent) for new window
            newSettingsWindow.initOwner(primaryStage);
            // Set position of second window, related to primary window.
            newSettingsWindow.setX(primaryStage.getX() + 200);
            newSettingsWindow.setY(primaryStage.getY() + 100);
            newSettingsWindow.show();
        });
    }

    private void showStage(Stage stage) {
        Scene scene = new Scene(borderPane);
        stage.setTitle("SameGame - 54915");
        stage.setMinWidth(780);
        stage.setMinHeight(600);
        stage.setMaxWidth(1000);
        stage.setMaxHeight(750);
        stage.setScene(scene);
        stage.show();
    }

    private void initializeButtons() {
        cb = new ComboBox<>();
        undoBt = new Button("<-");
        redoBt = new Button("->");
        deleteZone = new Button("Delete");
        cb.getItems().setAll(Level.values());
        cb.getSelectionModel().selectFirst();
        settingsBt = new Button("Settings");
        newGameBt = new Button("Start New Game");
        undoBt.setTextFill(Color.rgb(255, 255, 255));
        redoBt.setTextFill(Color.rgb(255, 255, 255));
        undoBt.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0), new CornerRadii(0), new Insets(0))));
        redoBt.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0), new CornerRadii(0), new Insets(0))));
        setActions();
    }

    private void setActions() {
        undoBt.setOnAction(e -> {
            controller.executeCommand(new UndoCommand());
        });
        redoBt.setOnAction(e -> {
            controller.executeCommand(new RedoCommand());
        });
        newGameBt.setOnAction(e -> executeNewGame());
        deleteZone.setOnAction(e -> {
            executeDelete();
        });
        cb.setOnAction(e -> executeNewGame());
    }

    private void executeNewGame() {
        controller.executeCommand(new StartNewGameCommand(getScaleValue(heightTf), getScaleValue(widthtTf), cb.getItems().get(cb.getSelectionModel().getSelectedIndex()).color));
    }

    private void executeDelete() {
        controller.executeCommand(new DeleteBiggestZoneCommand());
    }

    private int getScaleValue(TextFieldNumberBounded textFieldNumberBounded) {
        return textFieldNumberBounded.getText().equals("") ? -1 : Integer.parseInt(textFieldNumberBounded.getText());
    }

    private void initializeContainers() {
        borderPane = new BorderPane();
        gridPane = new GridPane();
        bottomVb = new VBox();
        arrowsVb = new VBox();
        infoVb = new VBox();
        scoreHb = new HBox();
        settingsHb = new HBox();
        gameTitleHb = new HBox();
        arrowsHb = new HBox();
        heightTf = new TextFieldNumberBounded(12, 20);
        widthtTf = new TextFieldNumberBounded(16, 24);
        setContainersStyle();
    }

    private void setContainersStyle() {
        infoVb.setSpacing(10);
        bottomVb.setSpacing(10);
        arrowsHb.setSpacing(10);
        scoreHb.setSpacing(30.0);
        settingsHb.setSpacing(15.0);
        gameTitleHb.setSpacing(80.0);
        infoVb.setAlignment(Pos.CENTER);
        scoreHb.setAlignment(Pos.CENTER);
        arrowsVb.setAlignment(Pos.CENTER);
        settingsHb.setAlignment(Pos.CENTER);
        gameTitleHb.setAlignment(Pos.CENTER);
        infoVb.setPadding(new Insets(0, 20, 0, 20));
        scoreHb.setPadding(new Insets(30, 0, 0, 0));
        arrowsVb.setPadding(new Insets(0, 10, 0, 10));
        settingsHb.setPadding(new Insets(10, 0, 20, 0));
        gameTitleHb.setPadding(new Insets(15, 0, 15, 0));
        gridPane.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0), new CornerRadii(0), new Insets(0))));
        borderPane.setBackground(new Background(new BackgroundFill(Color.rgb(228, 212, 196), new CornerRadii(0), new Insets(0))));
    }

    private void initializeLabels() {
        scoreLb = new Label();
        infLb = new Label("");
        clickScoreLb = new Label();
        remainingLb = new Label("");
        infoLb = new Label("INFO: ");
        ballsLb = new Label("BALLS:");
        titleLb = new Label("SameGame");
        scLb = new Label("Total Score: ");
        csLb = new Label("Last Click Score: ");
        levelLb = new Label("Select Level: ");
        setLabelsStyle();
    }

    private void setLabelsStyle() {
        scLb.setTextFill(Color.rgb(0, 0, 170));
        csLb.setTextFill(Color.rgb(0, 0, 170));
        scoreLb.setTextFill(Color.rgb(187, 0, 85));
        titleLb.setTextFill(Color.rgb(136, 0, 85));
        clickScoreLb.setTextFill(Color.rgb(162, 0, 119));
        scLb.setFont(new Font("Times New Roman", 16.5));
        csLb.setFont(new Font("Times New Roman", 13.5));
        infLb.setFont(new Font("Times New Roman", 13.5));
        titleLb.setFont(new Font("Times New Roman", 24));
        infoLb.setFont(new Font("Times New Roman", 13.5));
        scoreLb.setFont(new Font("Times New Roman", 16.5));
        levelLb.setFont(new Font("Times New Roman", 13.5));
        ballsLb.setFont(new Font("Times New Roman", 13.5));
        remainingLb.setFont(new Font("Times New Roman", 13.5));
        clickScoreLb.setFont(new Font("Times New Roman", 13.5));
        scLb.setBackground(new Background(new BackgroundFill(Color.rgb(221, 221, 221), new CornerRadii(0), new Insets(0))));
        csLb.setBackground(new Background(new BackgroundFill(Color.rgb(221, 221, 221), new CornerRadii(0), new Insets(0))));
        scoreLb.setBackground(new Background(new BackgroundFill(Color.rgb(221, 221, 221), new CornerRadii(0), new Insets(0))));
        clickScoreLb.setBackground(new Background(new BackgroundFill(Color.rgb(221, 221, 221), new CornerRadii(0), new Insets(0))));
    }

    @Override
    public void updateLeftBall(int newValue) {
        remainingLb.setText("" + newValue);
    }

    @Override
    public void showEndGameMessage(State state) {
        switch (state) {
            case WIN:
                infLb.setText("WON!");
                break;
            case LOST:
                infLb.setText("LOST!");
                break;
            default:
                infLb.setText("");
                break;
        }
    }

    @Override
    public void impossibleMove() {
        infLb.setText("NOP!");
        notification.play();
        notification.setOnFinished(e -> {
            infLb.setText("");
        });
    }

    @Override
    public void updateClickScore(int newValue) {
        clickScoreLb.setText("" + newValue);
    }

    @Override
    public void updateScore(int newValue) {
        scoreLb.setText("" + newValue);
    }

    @Override
    public void updateBoard(Ball[][] s) {
        gridPane.getChildren().remove(0, gridPane.getChildren().size());
        if (gridPane.getChildren().size() != s.length * s[0].length) {
            controller.updateBoard(s, gridPane);
        }
    }

    @Override
    public Position getPosition() {
        return null;
    }

    @Override
    public Command getCommand() {
        return null;
    }

    public Paint getColor(Ball ball) {
        if (ball != null) {
            return ballColor[ball.getColor() + 1];
        }
        return ballColor[0];
    }

    @Override
    public void setController(Controller controller) {
        this.controller = (FXController) controller;
    }

    public void highLight(ArrayList<Position> zone) {
        unHighLight();
        highlightBtnPos = zone;
        for (Position pos : zone) {
            Button btn = (Button) getNodeFromGridPane(gridPane, pos.getY(), pos.getX());
            assert btn != null;
            btn.setStyle("-fx-background-color: #FFFFFF;");
        }
    }

    private void unHighLight() {

        for (Position pos : highlightBtnPos) {
            Button btn = (Button) getNodeFromGridPane(gridPane, pos.getY(), pos.getX());
            if (btn != null) {
                btn.setStyle("-fx-background-color: #000000;");
            }
        }
    }

    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }
}
