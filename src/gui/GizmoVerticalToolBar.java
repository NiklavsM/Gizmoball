package gui;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.GridPane;

public class GizmoVerticalToolBar extends ToolBar {

    private final GridPane gridPane;
    private int rowIndex;

    public GizmoVerticalToolBar() {
        gridPane = new GridPane();
        rowIndex = 0;

        gridPane.setVgap(5);
        gridPane.setHgap(16);

        super.getItems().add(gridPane);
        super.setPadding(Theme.DEFAULT_PADDING);
    }

    public void setAlignment(Pos position) {
        gridPane.setAlignment(position);
    }

    public void add(Node node) {
        gridPane.add(node, 0, rowIndex++);
    }

    public void add(Node node, Label label) {
        gridPane.add(node, 0, rowIndex);
        gridPane.add(label, 1, rowIndex++);
    }


    public void setVGap(double value) {
        gridPane.setVgap(value);

    }

    public void setHGap(double value) {
        gridPane.setHgap(value);
    }
}
