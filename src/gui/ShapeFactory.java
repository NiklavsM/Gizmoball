package gui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.Ball;
import physics.Circle;
import physics.LineSegment;
import model.IGizmo;
import model.Wall;

public class ShapeFactory {
    private GraphicsContext graphicsContext;

    private double lScreenRatio;

    public ShapeFactory(Canvas canvas) {
        this.graphicsContext = canvas.getGraphicsContext2D();

        this.lScreenRatio = canvas.getWidth() / 20.0;
    }

    public void drawBody(IGizmo body) {
        if (body instanceof Ball)
            this.drawBall(body);

        if (body instanceof Wall)
            this.drawWall(body);
    }

    private void drawWall(IGizmo body) {

    }

    private void drawBall(IGizmo body) {

    }


}
