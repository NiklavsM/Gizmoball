package gui.panel;

import gui.PlayStage;
import gui.Theme;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.StrokeLineCap;
import model.Constants;
import model.Dot;
import model.IGameModel;
import model.gizmo.IGizmo;

import java.util.Observable;
import java.util.Observer;

public class BoardCanvasView extends Canvas implements Observer {

    private static final long serialVersionUID = 1L;
    protected IGameModel gameModel;
    GraphicsContext gc = this.getGraphicsContext2D();

    public BoardCanvasView(int width, int height, IGameModel gameModel) {
        super(width, height);

        gameModel.addObserver(this);
        this.gameModel = gameModel;
        redraw();
    }

    public void redraw() {

        int i, pxPerL = Constants.pxPerL;
        double diameter = 0;
        double xPoints[], yPoints[];

        gc.setFill(Theme.Colors.DEEP_BLUE);
        gc.fillRect(0, 0, super.getWidth(), super.getHeight());

        // Draw all the vertical lines
        gc.setFill(Theme.Colors.BLACK);

        for (IGizmo gizmo : gameModel.getGizmos()) {
            xPoints = new double[gizmo.getDots().size()];
            yPoints = new double[gizmo.getDots().size()];
            IGizmo.Type type = gizmo.getType();
            setGizmoColor(type);

            i = 0;

            for (Dot dot : gizmo.getDots()) {
                xPoints[i] = dot.getX() * pxPerL;
                yPoints[i] = dot.getY() * pxPerL;
                diameter = dot.getRadius() * 2 * pxPerL;
                i++;
            }
            if (type == IGizmo.Type.Flipper) {
                for (int k = 0; k < i; k++) {
                    gc.fillOval(xPoints[k] - diameter / 2, yPoints[k] - diameter / 2, diameter, diameter);
                }
                gc.setLineWidth(diameter);
                gc.setLineCap(StrokeLineCap.BUTT);
                gc.setStroke(Theme.Colors.ORANGE);
                gc.strokeLine(xPoints[0],yPoints[0],xPoints[1],yPoints[1]);
            }
            if (type == IGizmo.Type.Circle || type == IGizmo.Type.Ball) {
                gc.fillOval(xPoints[0] - diameter / 2, yPoints[0] - diameter / 2, diameter, diameter);
            } else {
                gc.fillPolygon(xPoints, yPoints, i);
            }
        }


    }

    private void setGizmoColor(IGizmo.Type type) {

        Paint fill;

        switch (type) {
            case Square:
                fill = Theme.Colors.RED;
                break;
            case Absorber:
                fill = Theme.Colors.PINK;
                break;
            case Triangle:
                fill = Theme.Colors.BLUE;
                break;
            case Circle:
                fill = Theme.Colors.GREEN;
                break;
            case Ball:
                fill = Theme.Colors.WHITE;
                break;
            case Flipper:
                fill = Theme.Colors.ORANGE;
                break;
            default:
                fill = Theme.Colors.WHITE;
                break;
        }

        gc.setFill(fill);

    }

    @Override
    public void update(Observable arg0, Object arg1) {
        repaint();
        redraw();
    }

    private void repaint() {
        GraphicsContext gc = this.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, super.getWidth(), super.getHeight());
    }

}
