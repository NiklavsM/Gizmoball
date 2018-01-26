package view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.Ball;
import model.Model;
import model.VerticalLine;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class BoardCanvasView extends Canvas implements Observer {


    private static final long serialVersionUID = 1L;
    protected int width;
    protected int height;
    protected Model gm;


    public BoardCanvasView(int w, int h, Model m) {
        // Observe changes in Model
        m.addObserver(this);
        width = w;
        height = h;
        gm = m;
//        this.setBorder(BorderFactory.createLineBorder(java.awt.Color.black));

        setup();
    }

    // Fix onscreen size
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    public void setup() {
        GraphicsContext g2 = this.getGraphicsContext2D();

        // Draw all the vertical lines
        for (VerticalLine vl : gm.getLines()) {
            g2.fillRect(vl.getX(), vl.getY(), vl.getWidth(), 1);
        }

        Ball b = gm.getBall();
        if (b != null) {
            g2.setFill(b.getColour());
            int x = (int) (b.getExactX() - b.getRadius());
            int y = (int) (b.getExactY() - b.getRadius());
            int width = (int) (2 * b.getRadius());
            g2.fillOval(x, y, width, width);
        }
    }

    @Override
    public void update(Observable arg0, Object arg1) {
        repaint();
    }

    private void repaint() {
        GraphicsContext gc = this.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, width, height);
    }

}
