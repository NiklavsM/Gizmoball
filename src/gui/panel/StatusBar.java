package gui.panel;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class StatusBar extends HBox {

    private Label statusBarLabel;

    public StatusBar(String statusBarText) {
        statusBarLabel = new Label(statusBarText);

        getStyleClass().add("statusbar");
        setPadding(new Insets(2, 2, 2, 16));

        setup();
    }

    public StatusBar() {
        this("Add some Gizmos to the map");
    }

    private void setup() {

        getChildren().add(statusBarLabel);
    }

    public String getText() {
        return statusBarLabel.getText();
    }

    public void setText(String statusBarText) {
        statusBarLabel.setText(statusBarText);
    }
}
