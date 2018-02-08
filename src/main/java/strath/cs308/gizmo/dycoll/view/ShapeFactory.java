package strath.cs308.gizmo.dycoll.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import mit.physics.Circle;
import mit.physics.LineSegment;
import strath.cs308.gizmo.dycoll.model.Ball;
import strath.cs308.gizmo.dycoll.model.IPhysicalBody;
import strath.cs308.gizmo.dycoll.model.Wall;

public class ShapeFactory
{
    private GraphicsContext graphicsContext;

    private double lScreenRatio;

    public ShapeFactory(Canvas canvas)
    {
        this.graphicsContext = canvas.getGraphicsContext2D();

        this.lScreenRatio = canvas.getWidth() / 20.0;
    }

    public void drawBody(IPhysicalBody body)
    {
        if (body instanceof Ball)
           this.drawBall(body);

        if (body instanceof Wall)
            this.drawWall(body);
    }

    private void drawWall(IPhysicalBody body)
    {
        LineSegment lineSegment = body.getLineSegments().get(0);

        this.graphicsContext.setStroke(Color.BLUE);
        this.graphicsContext.setLineWidth(0.5);
        this.graphicsContext.strokeLine(lineSegment.p1().x() * this.lScreenRatio
                , lineSegment.p1().y() * this.lScreenRatio
                , lineSegment.p2().x() * this.lScreenRatio
                , lineSegment.p2().y() * this.lScreenRatio);
    }

    private void drawBall(IPhysicalBody body)
    {
        this.graphicsContext.setFill(Color.GREEN);

        Circle circle = body.getCircles().get(0) ;

        this.graphicsContext.fillOval((circle.getCenter().x() - circle.getRadius()) * this.lScreenRatio
                , (circle.getCenter().y() - circle.getRadius()) * this.lScreenRatio
                , circle.getRadius() * this.lScreenRatio
                , circle.getRadius() * this.lScreenRatio);
    }


}
