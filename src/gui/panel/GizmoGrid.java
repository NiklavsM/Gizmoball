package gui.panel;

import gui.Theme;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;


public abstract class GizmoGrid extends VBox {


    private GridPane gridPane;
    private int rowIndex;
    private int colindex;

    public GizmoGrid() {
        gridPane = new GridPane();

        setSpacing(16);
        setPadding(Theme.Padding.DEFAULT_PADDING);

        setup();
    }

    private void setup() {
        // Heading
        Label titleLabel = new Label("Gizmos");
        titleLabel.setFont(Theme.Fonts.CARD_TITLE);

        ScrollPane scrollpane = new ScrollPane();
        scrollpane.setContent(gridPane);
        scrollpane.setMaxHeight(250);


        gridPane.setHgap(16);
        gridPane.setVgap(16);
        gridPane.setMaxHeight(100);
        gridPane.setMinWidth(200);
        gridPane.setAlignment(Pos.CENTER);

        getChildren().addAll(titleLabel, scrollpane);
    }

    public String getPanelName() {
        return "Gizmos";
    }

    public void addGizmo(Node graphic, String gizmoName) {

        Label label = new Label(gizmoName);
        label.getStyleClass().add("gizmogrid");
        label.setFont(Theme.Fonts.REGULAR_FONT);

        gridPane.add(graphic, colindex, rowIndex);
        gridPane.add(label, colindex++, rowIndex + 1);
        if (colindex == 3) {
            rowIndex += 2;
            colindex = 0;
        }
    }

}
