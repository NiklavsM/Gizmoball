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
        } else
            setGizmoColor(type);

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

    private void setGizmoColor(IGizmo.Type type) {
        switch (type) {
            case BALL:
                gc.setStroke(Color.WHITE);
                break;

            case SQUARE:
                gc.setFill(RED);
                break;

            case ABSORBER:
                gc.setFill(PINK);
                break;

            case CIRCLE:
                gc.setStroke(GREEN);
                break;

            case WALLS:
                gc.setFill(Color.BLACK);
                break;

            case TRIANGLE:
                gc.setFill(BLUE);
                break;

            case FLIPPER:
                gc.setStroke(ORANGE);
                break;

            case LEFT_FLIPPER:
                gc.setStroke(ORANGE);
                break;

            case RIGHT_FLIPPER:
                gc.setStroke(ORANGE);
                break;
        }
    }

    public void previewGizmo(IGizmo gizmo, double x, double y) {
//        x = x * pxPerL;
//        y = y* pxPerL;
//        gc.setFill(Color.LAWNGREEN);
//        gc.setStroke(Color.LAWNGREEN);
//        switch (gizmo) {
//            case SQUARE:
//                gc.strokeLine(x, y, x+1, y);
//                gc.strokeLine(x+1, y, x+1, y+1);
//                gc.strokeLine(x+1, y+1, x, y+1);
//                gc.strokeLine(x, y+1, x, y);
//                break;
//        }
//
//

    }
}
