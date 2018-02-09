package gui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import model.IGizmo;

public class GizmoDrawer
{
    private GraphicsContext graphicsContext;
    private final double lPixelRatio;

    public GizmoDrawer(Canvas canvas) {
        lPixelRatio = canvas.getWidth() / 20.0;
        graphicsContext = canvas.getGraphicsContext2D();
    }

    public void drawGizmo(IGizmo gizmo) {
        /**
         * TODO
         * draw gizmo
         */
    }

}
