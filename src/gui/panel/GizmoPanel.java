package gui.panel;

import controller.editor.gizmo.*;
import gui.Theme;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import model.EditorModel;

public class GizmoPanel extends GizmoGrid {

    private EditorModel editorModel;

    public GizmoPanel(EditorModel editorModel) {
        this.editorModel = editorModel;
        setup();
    }

    private void setup() {
        Rectangle squareShape = new Rectangle(25, 25, Theme.Colors.RED);
        addItem(squareShape, "Square", new SquareGizmoListener(editorModel));

        Circle circleShape = new Circle(12.5, Theme.Colors.BLUE);
        addItem(circleShape, "Dot", new CircleGizmoHandler(editorModel));

        Image triangleShape = new Image(getClass().getClassLoader().getResourceAsStream("assets/icons/tri.png"));
        ImageView triangle = new ImageView();
        triangle.setFitWidth(25);
        triangle.setFitHeight(25);
        triangle.setImage(triangleShape);
        addItem(triangle, "Triangle", new TriangleGizmoHandler(editorModel));

        Rectangle absorber = new Rectangle(25, 15, Theme.Colors.PINK);
        addItem(absorber, "Absorber", new AbsorberGizmoHandler(editorModel));

        Image flipperShape = new Image(getClass().getClassLoader().getResourceAsStream("assets/icons/flipper.png"));
        ImageView flipper = new ImageView();
        flipper.setFitWidth(30);
        flipper.setFitHeight(30);
        flipper.setImage(flipperShape);
        addItem(flipper, "Flipper", new FlipperGizmoHandler(editorModel));

        Circle ballShape = new Circle(9, Theme.Colors.ORANGE);
        addItem(ballShape, "Ball", new BallGizmoHandler());


        getStyleClass().add("GizmoPane");
    }


    private void addItem(Node graphic, String gizmoName, EventHandler<ActionEvent> eventEventHandler) {
        Button button = new Button();
        button.setGraphic(graphic);
        button.setOnAction(eventEventHandler);

        addEntity(button, gizmoName);
    }
}
