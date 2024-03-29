package strath.cs308.gizmoball.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.StrokeLineCap;
import strath.cs308.gizmoball.model.Dot;
import strath.cs308.gizmoball.model.gizmo.IGizmo;
import strath.cs308.gizmoball.utils.Settings;

import java.util.List;

public class GizmoDrawer {


    static final Paint DEEP_BLUE = Color.rgb(84, 110, 122);

    private final double pxPerL;
    private GraphicsContext gc;

    public GizmoDrawer(Canvas canvas) {
        pxPerL = canvas.getWidth() / 20.0;
        gc = canvas.getGraphicsContext2D();
    }

    void drawGizmo(IGizmo gizmo, boolean isPreview) {


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
                    || type.equals(IGizmo.Type.RIGHT_FLIPPER)
                    || type.equals(IGizmo.Type.SPINNER)) {

                if (!isPreview && "true".equals(Settings.getProperty("shadowsEnabled"))) {
                    enableShadow();
                }

                gc.setLineWidth(diameter);
                gc.setLineCap(StrokeLineCap.ROUND);
                if (type.equals(IGizmo.Type.SPINNER) && i == 4) {
                    gc.strokeLine(xPoints[0], yPoints[0], xPoints[1], yPoints[1]);
                    gc.strokeLine(xPoints[2], yPoints[2], xPoints[3], yPoints[3]);
                } else {
                    gc.strokeLine(xPoints[0], yPoints[0], xPoints[i - 1], yPoints[i - 1]);
                }
            } else {
                gc.fillPolygon(xPoints, yPoints, i);
            }
        }

        gc.setEffect(null);
    }

    private void enableShadow() {
        Color b = Color.rgb(0, 0, 0, 0.25);
        gc.setEffect(new DropShadow(5, 7, 7, b));

    }

}
