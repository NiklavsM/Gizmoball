package gui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import mit.physics.Circle;
import model.IGizmo;

public class GizmoDrawer
{
    private static final Color GREEN = Color.rgb(139, 30, 99);

    private GraphicsContext graphicsContext;
    private final double lPixelRatio;

    public GizmoDrawer(Canvas canvas) {
        lPixelRatio = canvas.getWidth() / 20.0;
        graphicsContext = canvas.getGraphicsContext2D();
    }

    public void drawGizmo(IGizmo gizmo) {
        switch (gizmo.getType()) {
            case Ball:
                drawBall(gizmo);
            break;
        }
    }

    private void drawBall(IGizmo gizmo) {
        graphicsContext.setFill(GizmoDrawer.GREEN);
        Circle circle = gizmo.getCircles().iterator().next();

        graphicsContext.fillOval((circle.getCenter().x() - circle.getRadius()) * lPixelRatio
                , (circle.getCenter().y() - circle.getRadius() ) *lPixelRatio
                , circle.getRadius() * 2 * lPixelRatio
                , circle.getRadius() * 2 * lPixelRatio);
    }

}
