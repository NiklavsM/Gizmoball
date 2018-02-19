package gui.panel;

import gui.Theme;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import model.*;
import model.gizmo.IEntity;

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


        gc.setFill(Theme.Colors.DEEP_BLUE);
        gc.fillRect(0, 0, super.getWidth(), super.getHeight());

        // Draw all the vertical lines
        gc.setFill(Theme.Colors.BLACK);

        // Render Gizmos
        renderGizmos();

        renderBall();
    }

    private void renderGizmos() {
        for (IEntity gizmo : gameModel.getEntities()) {

            IEntity.Type type = gizmo.getType();

            RenderDetails renderDetails = new RenderDetails(gizmo);

            int numberOfEdges = renderDetails.getNumberOfEdges();
            double diameter = renderDetails.getDiameter();
            double[] xPoints = renderDetails.getxPoints();
            double[] yPoints = renderDetails.getyPoints();

            gc.setFill(renderDetails.getFill());

            if (type == IEntity.Type.FLIPPER) {
                for (int k = 0; k < numberOfEdges; k++) {
                    gc.fillOval(xPoints[k] - diameter / 2, yPoints[k] - diameter / 2, diameter, diameter);
                }
                gc.setLineWidth(diameter);
                gc.setLineCap(StrokeLineCap.BUTT);
                gc.setStroke(Theme.Colors.ORANGE);
                gc.strokeLine(xPoints[0], yPoints[0], xPoints[1], yPoints[1]);
            }

            if (type == IEntity.Type.CIRCLE || type == IEntity.Type.BALL) {
                gc.fillOval(xPoints[0] - diameter / 2, yPoints[0] - diameter / 2, diameter, diameter);
            } else {
                gc.fillPolygon(xPoints, yPoints, numberOfEdges);
            }
        }
    }

    private void renderBall() {
        Ball ball = gameModel.getBall();
        RenderDetails renderDetails = new RenderDetails(ball);
        double diameter = renderDetails.getDiameter();
        double[] xPoints = renderDetails.getxPoints();
        double[] yPoints = renderDetails.getyPoints();

        gc.fillOval(xPoints[0] - diameter / 2, yPoints[0] - diameter / 2, diameter, diameter);
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
