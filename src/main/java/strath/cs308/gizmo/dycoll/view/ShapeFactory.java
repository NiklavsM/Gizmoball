package strath.cs308.gizmo.dycoll.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import strath.cs308.gizmo.dycoll.model.IPhysicalBody;

public class ShapeFactory
{
    private GraphicsContext graphicsContext;

    private double widthRatio;
    private double heightRatio;

    public ShapeFactory(Canvas canvas)
    {
        this.graphicsContext = canvas.getGraphicsContext2D();
        this.widthRatio = canvas.getWidth() / 20.0;
        this.heightRatio = canvas.getHeight() / 20.0;
    }

    void drawBody(IPhysicalBody body)
    {
        
    }
}
