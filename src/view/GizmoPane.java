package view;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;

public class GizmoPane extends MenuPanel {


    private VBox vbox;

    public GizmoPane() {
        vbox = new VBox();

        getChildren().add(vbox);

        setup();
    }

    private void setup() {
        vbox.setSpacing(16);
        vbox.setPadding(new Insets(16, 16, 16, 16));
        vbox.setMaxHeight(40);
        vbox.setMinWidth(200);

        VBox titleBox = new VBox();
        Label titleLabel = new Label(getPanelName());
        titleLabel.setFont(Kit.TITLE_FONT);

        titleBox.getChildren().add(titleLabel);

        vbox.getChildren().add(titleLabel);
    }

    public String getPanelName() {
        return "Gizmos";
    }

    public void addGizmo(Shape symbol, String gizmoName) {

        HBox gizmoBar = new HBox();
        gizmoBar.setSpacing(16);
        Label label = new Label(gizmoName);
        label.setFont(Kit.REGULAR_FONT);

        gizmoBar.getChildren().add(symbol);
        gizmoBar.getChildren().add(label);

        vbox.getChildren().addAll(gizmoBar, new Separator());
    }
}
