package gui.toolbar;

import gui.Theme;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.GridPane;

public class GizmoHorizontalToolBar extends ToolBar {

    private final GridPane gridPane;
    private int columnIndex;

    public GizmoHorizontalToolBar() {
        gridPane = new GridPane();
        columnIndex = 0;

        gridPane.setVgap(5);
        gridPane.setHgap(16);

        super.getItems().add(gridPane);
        super.setPadding(Theme.Padding.DEFAULT_PADDING);
    }

    public void setAlignment(Pos position) {
        gridPane.setAlignment(position);
    }

    public void add(Node node) {
        gridPane.add(node, columnIndex++, 0);
    }

    /**
     * Add an item to the toolbar
     */
    public void add(Node node, Label label) {
        gridPane.add(node, columnIndex, 0);
        gridPane.add(label, columnIndex++, 1);
    }

    public void setVGap(double value) {
        gridPane.setVgap(value);

    }

    public void setHGap(double value) {
        gridPane.setHgap(value);
    }

}
