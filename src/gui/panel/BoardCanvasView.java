package gui.panel;

import gui.Theme;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.Ball;
import model.Constants;
import model.GameModel;
import model.IGizmo;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class BoardCanvasView extends Canvas implements Observer {


    private static final long serialVersionUID = 1L;
    protected GameModel gm;

    public BoardCanvasView(int width, int height, GameModel gameModel) {
        super(width, height);
        // Observe changes in Model
        gameModel.addObserver(this);
        gm = gameModel;
//        this.setBorder(BorderFactory.createLineBorder(java.awt.Color.black));
        redraw();
    }

    // Fix onscreen size
    public Dimension getPreferredSize() {
        return new Dimension((int) super.getWidth(), (int) super.getHeight());
    }

    public void redraw() {
        GraphicsContext gc = this.getGraphicsContext2D();

        gc.setFill(Theme.Colors.DEEP_BLUE);
        gc.fillRect(0, 0, super.getWidth(), super.getHeight());

        // Draw all the vertical lines
        gc.setFill(javafx.scene.paint.Color.BLACK);

        for (IGizmo gizmo : gm.getIGizmos()) {
            double gizmoStartX;
            double gizmoStartY;
            double gizmoEndX;
            double gizmoEndY;
            if (gizmo.getType() == IGizmo.Type.Square) {
                gizmoStartX = gizmo.getRootCircle().getCenter().x();
                gizmoStartY = gizmo.getRootCircle().getCenter().y();
                gc.setFill(Theme.Colors.RED);
                gc.fillRect(gizmoStartX * Constants.pxPerL, gizmoStartY * Constants.pxPerL, 1 * Constants.pxPerL, 1 * Constants.pxPerL);
            }
            if (gizmo.getType() == IGizmo.Type.Absorber) {
                gizmoStartX = gizmo.getRootCircle().getCenter().x();
                gizmoStartY = gizmo.getRootCircle().getCenter().y();
                gizmoEndX = gizmo.getEndCircle().getX();
                gizmoEndY = gizmo.getEndCircle().getY();
                gc.setFill(Theme.Colors.PINK);
                gc.fillRect(gizmoStartX * Constants.pxPerL, gizmoStartY * Constants.pxPerL, (gizmoEndX - gizmoStartX) * Constants.pxPerL, (gizmoEndY - gizmoStartY) * Constants.pxPerL);
            }
        }

        Ball b = gm.getBall();
        if (b != null) {
            gc.setFill(b.getColour());
            int x = (int) ((b.getExactX() - b.getRadius()) * Constants.pxPerL);
            int y = (int) ((b.getExactY() - b.getRadius()) * Constants.pxPerL);
            int width = (int) (2 * b.getRadius() * Constants.pxPerL);
            gc.fillOval(x, y, width, width);
        }

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
