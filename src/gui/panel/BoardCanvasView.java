package gui.panel;

import gui.Theme;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.*;
import model.gizmo.IGizmo;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class BoardCanvasView extends Canvas implements Observer {

    private static final long serialVersionUID = 1L;
    protected IGameModel gm;

    public BoardCanvasView(int width, int height, IGameModel gameModel) {
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
        int pxPerL = Constants.pxPerL;
        int i;

        gc.setFill(Theme.Colors.DEEP_BLUE);
        gc.fillRect(0, 0, super.getWidth(), super.getHeight());

        // Draw all the vertical lines
        gc.setFill(javafx.scene.paint.Color.BLACK);

        for (IGizmo gizmo : gm.getGizmos()) {

            if (gizmo.getType() == IGizmo.Type.Square) {
                gc.setFill(Theme.Colors.RED);
            } else if (gizmo.getType() == IGizmo.Type.Absorber) {
                gc.setFill(Theme.Colors.PINK);
            } else if (gizmo.getType() == IGizmo.Type.Triangle) {
                gc.setFill(Theme.Colors.BLUE);
            }
            double xPoints[] = new double[10];
            double yPoints[] = new double[10];
            i = 0;
            for (Dot dot : gizmo.getDots()) {
                xPoints[i] = dot.getX() * pxPerL;
                yPoints[i] = dot.getY() * pxPerL;
                i++;
            }
            if (gizmo.getType() == IGizmo.Type.Circle) {
                gc.setFill(Theme.Colors.GREEN);
                gc.fillOval(xPoints[0] - 0.5 * pxPerL, yPoints[0] - 0.5 * pxPerL, pxPerL, pxPerL);
            } else {
                gc.fillPolygon(xPoints, yPoints, i);
            }
        }

        Ball b = gm.getBall(); // could get rid of this if the ball implements IGizmo
        if (b != null) {
            gc.setFill(b.getColour());
            int x = (int) ((b.getExactX() - b.getRadius()) * pxPerL);
            int y = (int) ((b.getExactY() - b.getRadius()) * pxPerL);
            int width = (int) (2 * b.getRadius() * pxPerL);
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
