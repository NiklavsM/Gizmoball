package strath.cs308.gizmoball.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.StrokeLineCap;
import strath.cs308.gizmoball.model.Dot;
import strath.cs308.gizmoball.model.gizmo.IGizmo;

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

    public void drawGizmo(IGizmo gizmo) {
        gc.setFill(Color.BLACK);

        double diameter = 0;
        double[] xPoints = new double[gizmo.getDots().size()];
        double[] yPoints = new double[gizmo.getDots().size()];
        IGizmo.Type type = gizmo.getType();
        setGizmoColor(type);

        int i = 0;

        for (Dot dot : gizmo.getDots()) {
            xPoints[i] = dot.getX() * pxPerL;
            yPoints[i] = dot.getY() * pxPerL;
            diameter = dot.getRadius() * 2 * pxPerL;
            i++;
        }
        
        if (type == IGizmo.Type.CIRCLE || type == IGizmo.Type.BALL || type == IGizmo.Type.FLIPPER) {
            gc.setLineWidth(diameter);
            gc.setLineCap(StrokeLineCap.ROUND);
            gc.strokeLine(xPoints[0], yPoints[0], xPoints[i-1], yPoints[i-1]);
        } else {
            gc.fillPolygon(xPoints, yPoints, i);
        }
    }

    private void setGizmoColor(IGizmo.Type type) {
        switch (type) {
            case BALL:
                gc.setFill(Color.WHITE);
                break;

            case SQUARE:
                gc.setFill(RED);
                break;

            case ABSORBER:
                gc.setFill(PINK);
                break;

            case CIRCLE:
                gc.setFill(GREEN);
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
        }

    }
}
