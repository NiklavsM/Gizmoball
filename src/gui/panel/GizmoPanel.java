package gui.panel;

import gui.Theme;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class GizmoPanel extends VBox {

    public GizmoPanel() {

        setSpacing(16);
        setPadding(Theme.Padding.DEFAULT_PADDING);

        setup();
    }

    private void setup() {
        // Heading
        Label titleLabel = new Label("Gizmos");
        titleLabel.setFont(Theme.Fonts.CARD_TITLE);

        // Content
        ScrollPane scrollpane = new ScrollPane();
        GizmoGrid gizmogrid = new GizmoGrid();

        Rectangle squareShape = new Rectangle(25, 25, Theme.Colors.BLUE);
        gizmogrid.addGizmo(squareShape, "Square");

        Circle circleShape = new Circle(12.5, Theme.Colors.RED);
        gizmogrid.addGizmo(circleShape, "Circle");

        Image triangleShape = new Image(getClass().getClassLoader().getResourceAsStream("assets/icons/tri.png"));
        ImageView triangle = new ImageView();
        triangle.setFitWidth(25);
        triangle.setFitHeight(25);
        triangle.setImage(triangleShape);
        gizmogrid.addGizmo(triangle, "Triangle");

        Rectangle absorber = new Rectangle(25, 15, Theme.Colors.PURPLE);
        gizmogrid.addGizmo(absorber, "Absorber");

        Image flipperShape = new Image(getClass().getClassLoader().getResourceAsStream("assets/icons/flipper.png"));
        ImageView flipper = new ImageView();
        flipper.setFitWidth(30);
        flipper.setFitHeight(30);
        flipper.setImage(flipperShape);
        gizmogrid.addGizmo(flipper, "Flipper");

        Circle ballShape = new Circle(9, Theme.Colors.ORANGE);
        gizmogrid.addGizmo(ballShape, "Ball");

        scrollpane.setContent(gizmogrid);
        scrollpane.setMaxHeight(250);

        getStyleClass().add("GizmoPane");
        getChildren().addAll(titleLabel, scrollpane);
    }
}
