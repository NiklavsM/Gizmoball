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

            default:
                drawPoly(gizmo);
            break;
        }
    }

    private void drawPoly(IGizmo gizmo)
    {
        graphicsContext.setFill(Color.PURPLE);
        gizmo.getCircles().forEach(circle -> {
            graphicsContext.fillOval((circle.getCenter().x() - circle.getRadius()) * lPixelRatio
                , (circle.getCenter().y() - circle.getRadius() ) *lPixelRatio
                , circle.getRadius() * 2 * lPixelRatio
                , circle.getRadius() * 2 * lPixelRatio);
        });

        graphicsContext.setLineWidth(1);
        gizmo.getLines().forEach(lineSegment -> {
            graphicsContext.strokeLine(lineSegment.p1().x() * lPixelRatio
                    , lineSegment.p1().y() * lPixelRatio
                    , lineSegment.p2().x() * lPixelRatio
                    , lineSegment.p2().y() * lPixelRatio);
        });

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
