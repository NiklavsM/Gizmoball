package view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.GridPane;

public class GizmoToolbar extends ToolBar {

    private GridPane gridPane;
    private int columnIndex;

    public GizmoToolbar() {
        gridPane = new GridPane();
        gridPane.setVgap(5);
        gridPane.setHgap(16);
        gridPane.setAlignment(Pos.CENTER);
        columnIndex = 0;
        setup();
    }

    private void setup() {
        addItem("Play", "play-button");
        addItem("Load", "load-button");
        addItem("Save", "save-button");
        addItem("Save As", "saveas-button");
        addItem("Clear Board", "clear-button");
        addItem("Undo", "undo-button");
        addItem("Redo", "redo-button");

        addItem("Toggle Grid", "grid-button");

        super.getItems().add(gridPane);
        super.setPadding(Theme.DEFAULT_PADDING);
    }


    private void addItem(String text, String cssClass) {
        Button button = new Button();
        button.setMaxSize(24, 24);
        button.setMinSize(24, 24);
        button.getStyleClass().add(cssClass);

        Label label = new Label(text);
        label.setFont(Theme.Fonts.REGULAR_FONT);

        gridPane.add(button, columnIndex, 0);
        gridPane.add(label, columnIndex++, 1);
    }
}