package strath.cs308.gizmoball.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.StrokeLineCap;
import strath.cs308.gizmoball.model.Dot;
import strath.cs308.gizmoball.model.gizmo.IGizmo;

import java.util.List;

public class GizmoDrawer {

    public static final Paint RED = Color.rgb(244, 67, 54);
    public static final Paint PINK = Color.rgb(233, 30, 99);
    public static final Paint GREEN = Color.rgb(139, 195, 74);
    public static final Paint BLUE = Color.rgb(3, 169, 244);
    public static final Paint ORANGE = Color.rgb(255, 152, 0);
    public static final Paint DEEP_BLUE = Color.rgb(84, 110, 122);
    public static final Paint YELLOW = Color.rgb(255, 255, 23);
    public static final Paint CYAN = Color.rgb(0, 255, 226);

    private final double pxPerL;
    private GraphicsContext gc;

    public GizmoDrawer(Canvas canvas) {
        pxPerL = canvas.getWidth() / 20.0;
        gc = canvas.getGraphicsContext2D();
    }

    public void drawGizmo(IGizmo gizmo, boolean isPreview) {
        gc.setFill(Color.BLACK);
        List<Dot> dots = gizmo.getDots();
        double diameter = 0;
        double[] xPoints = new double[dots.size()];
        double[] yPoints = new double[dots.size()];
        IGizmo.Type type = gizmo.getType();

        if (isPreview) {
            gc.setFill(Color.GAINSBORO);
            gc.setStroke(Color.GAINSBORO);
        } else {
            gc.setFill(Color.web(gizmo.getColor()));
            gc.setStroke(Color.web(gizmo.getColor()));
        }

        int i = 0;
        for (Dot dot : dots) {
            xPoints[i] = dot.getX() * pxPerL;
            yPoints[i] = dot.getY() * pxPerL;
            diameter = dot.getRadius() * 2 * pxPerL;
            i++;
        }
        if (i > 0) {
            if (type.equals(IGizmo.Type.CIRCLE)
                    || type.equals(IGizmo.Type.BALL)
                    || type.equals(IGizmo.Type.FLIPPER)
                    || type.equals(IGizmo.Type.LEFT_FLIPPER)
                    || type.equals(IGizmo.Type.RIGHT_FLIPPER)) {
                gc.setLineWidth(diameter);
                gc.setLineCap(StrokeLineCap.ROUND);
                gc.strokeLine(xPoints[0], yPoints[0], xPoints[i - 1], yPoints[i - 1]);
            } else {
                gc.fillPolygon(xPoints, yPoints, i);
            }
        }

    }

}
