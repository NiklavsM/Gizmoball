package view;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;

public class MouseToolbar extends ToolBar {

    private int rowIndex;
    private GridPane gridPane;

    public MouseToolbar() {
        rowIndex = 0;
        setup();
    }

    private void setup() {

        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(16);


        getItems().addAll(gridPane);

        addItem("Add Tool", "add-button");
        addItem("Connect Tool", "connect-button");
        addItem("Rotate tool", "rotate-button");

        setPadding(Theme.DEFAULT_PADDING);
        setOrientation(Orientation.VERTICAL);

        getStyleClass().add("toolbar");
    }


    private void addItem(String toolname, String className) {
        Button button = new Button();
        button.setScaleX(0.8);
        button.setScaleY(0.8);

        Tooltip tooltip = new Tooltip(toolname);
        button.setTooltip(tooltip);
        button.getStyleClass().add(className);

        gridPane.add(button, 0, rowIndex++);
    }
}