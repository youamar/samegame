package g54915.samegame.view;

import javafx.geometry.Pos;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.VBox;
import java.util.regex.Pattern;

public class TextFieldNumberBounded extends VBox {

    private final TextField textField;
    private Label label;
    private int min, max;

    public TextFieldNumberBounded() {
        this(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public TextFieldNumberBounded(int min, int max) {
        super();
        // todo vérifier que min < max
        this.min = min;
        this.max = max;
        textField = new TextField();
        textField.setPrefWidth(250);
        textField.setTextFormatter(new TextFormatter<String>(
                change -> {
                    if (Pattern.matches("\\d*", change.getText())) {
                        return change;
                    } else {
                        label.setText("Value is numeric");
                        return null;
                    }
                }
        ));
        textField.focusedProperty().addListener(
                (observableValue, oldValue, newValue) -> {
                    if (newValue) {
                        // on focus in, clear text field
                        textField.setText("");
                    } else {
                        // on focus out, check bounds
                        var text = textField.getText();
                        if (text != "") {
                            var n = Integer.parseInt(textField.getText());
                            if (!(n >= min && n <= max)) {
                                label.setText("Value must be between "
                                        + min + " and " + max);
                                textField.requestFocus();
                            }
                        }
                    }
                });

        label = new Label();
        label.setStyle("-fx-text-fill: #c70015;");
        setAlignment(Pos.CENTER_LEFT);
        setFillWidth(false);
        setPrefWidth(textField.getPrefWidth());
        setMaxWidth(Control.USE_PREF_SIZE);
        getChildren().add(textField);
        getChildren().add(label);
    }

    @Override
    public void requestFocus() {
        textField.requestFocus();
    }

    public String getText() {
        return textField.getText();
    }

    public void setText(String text) {
        textField.setText(text);
    }

}
