package gui.panel;

import gui.Theme;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.MITBall;
import model.Constants;
import model.Model;
import model.VerticalLine;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class BoardCanvasView extends Canvas implements Observer {


    private static final long serialVersionUID = 1L;
    protected Model gm;

    public BoardCanvasView(int width, int height, Model model) {
        super(width, height);
        // Observe changes in Model
        model.addObserver(this);
        gm = model;
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
        for (VerticalLine vl : gm.getLines()) {
            gc.fillRect(vl.getX() * Constants.pxPerL, vl.getY() * Constants.pxPerL, vl.getWidth() * Constants.pxPerL, 1);
        }

        MITBall b = gm.getMITBall();
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
