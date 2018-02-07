package strath.cs308.gizmo.dycoll.view;

import javafx.scene.canvas.GraphicsContext;
import strath.cs308.gizmo.dycoll.model.IPhysicalBody;

public class ShapeFactory
{
    private GraphicsContext graphicsContext;

    public ShapeFactory(GraphicsContext graphicsContext)
    {
        this.graphicsContext = graphicsContext;
    }

    void drawBody(IPhysicalBody body)
    {

    }
}
