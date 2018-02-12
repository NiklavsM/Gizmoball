package gui.panel;

import controller.editor.AddGizmoHandler;
import gui.Theme;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;


public class GizmoGrid extends Group {


    private GridPane gridPane;
    private int rowIndex;
    private int colindex;

    public GizmoGrid() {
        gridPane = new GridPane();
        getChildren().add(gridPane);
        setup();
    }

    private void setup() {
        gridPane.setHgap(16);
        gridPane.setVgap(16);
        gridPane.setMaxHeight(100);
        gridPane.setMinWidth(200);
        gridPane.setAlignment(Pos.CENTER);

    }

    public String getPanelName() {
        return "Gizmos";
    }

    public void addGizmo(Node symbol, String gizmoName) {

        Label label = new Label(gizmoName);
        label.getStyleClass().add("gizmogrid");
        label.setFont(Theme.Fonts.REGULAR_FONT);
        label.addEventHandler(MouseEvent.MOUSE_CLICKED,new AddGizmoHandler());
        symbol.addEventHandler(MouseEvent.MOUSE_CLICKED,new AddGizmoHandler());
        gridPane.add(symbol, colindex, rowIndex);
        gridPane.add(label, colindex++, rowIndex + 1);
        if (colindex == 3) {
            rowIndex += 2;
            colindex = 0;
        }
    }

}
