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
        LineSegment lineSegment = body.getLines().get(0);

        this.graphicsContext.setStroke(Color.BLUE);
        this.graphicsContext.setLineWidth(0.5);
        this.graphicsContext.strokeLine(lineSegment.p1().x() * this.lScreenRatio
                , lineSegment.p1().y() * this.lScreenRatio
                , lineSegment.p2().x() * this.lScreenRatio
                , lineSegment.p2().y() * this.lScreenRatio);
    }

    private void drawBall(IGizmo body) {
        this.graphicsContext.setFill(Color.GREEN);

        Circle circle = body.getCircles().get(0);

        this.graphicsContext.fillOval((circle.getCenter().x() - circle.getRadius()) * this.lScreenRatio
                , (circle.getCenter().y() - circle.getRadius()) * this.lScreenRatio
                , circle.getRadius() * this.lScreenRatio
                , circle.getRadius() * this.lScreenRatio);
    }


}
