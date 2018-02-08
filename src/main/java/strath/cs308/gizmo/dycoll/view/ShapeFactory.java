package strath.cs308.gizmo.dycoll.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import mit.physics.Circle;
import strath.cs308.gizmo.dycoll.model.Ball;
import strath.cs308.gizmo.dycoll.model.IPhysicalBody;

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
