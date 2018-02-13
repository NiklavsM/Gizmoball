package gui.panel;

import controller.editor.gizmo.*;
import gui.Theme;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class GizmoPanel extends GizmoGrid {

    public GizmoPanel() {

        setup();
    }

    private void setup() {
        Rectangle squareShape = new Rectangle(25, 25, Theme.Colors.RED);
        addItem(squareShape, "Square", new SquareGizmoListener());

        Circle circleShape = new Circle(12.5, Theme.Colors.BLUE);
        addItem(circleShape, "Dot", new DotGizmoHandler());

        Image triangleShape = new Image(getClass().getClassLoader().getResourceAsStream("assets/icons/tri.png"));
        ImageView triangle = new ImageView();
        triangle.setFitWidth(25);
        triangle.setFitHeight(25);
        triangle.setImage(triangleShape);
        addItem(triangle, "Triangle", new TriangleGizmoHandler());

        Rectangle absorber = new Rectangle(25, 15, Theme.Colors.PINK);
        addItem(absorber, "Absorber", new AbsorberGizmoHandler());

        Image flipperShape = new Image(getClass().getClassLoader().getResourceAsStream("assets/icons/flipper.png"));
        ImageView flipper = new ImageView();
        flipper.setFitWidth(30);
        flipper.setFitHeight(30);
        flipper.setImage(flipperShape);
        addItem(flipper, "Flipper", new FlipperGizmoHandler());

        Circle ballShape = new Circle(9, Theme.Colors.ORANGE);
        addItem(ballShape, "Ball", new BallGizmoHandler());


        getStyleClass().add("GizmoPane");
    }


    private void addItem(Node graphic, String gizmoName, EventHandler<ActionEvent> eventEventHandler) {
        Button button = new Button();
        button.setGraphic(graphic);
        button.setOnAction(eventEventHandler);

        addGizmo(button, gizmoName);
    }
}
