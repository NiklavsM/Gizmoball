package gui.panel;

import gui.Theme;
import javafx.scene.paint.Paint;
import model.Ball;
import model.Dot;
import model.gizmo.Gizmo;
import model.gizmo.IEntity;

import static model.Constants.pxPerL;

public class RenderDetails {

    private IEntity entity;
    private double diameter;
    private double[] xPoints;
    private double[] yPoints;
    private Paint color;
    private int numberOfEdges;


    public RenderDetails(IEntity entity) {
        this.entity = entity;

        if (entity.getType() == IEntity.Type.Ball) {
            makeBall();
        } else {
            makeGizmo();
        }
    }

    private void makeBall() {
        Ball ball = (Ball) entity;
        xPoints = new double[ball.getDots().size()];
        yPoints = new double[ball.getDots().size()];

        IEntity.Type type = ball.getType();

        setGizmoColor(type);

        numberOfEdges = 0;

        for (Dot dot : ball.getDots()) {
            xPoints[numberOfEdges] = dot.getX() * pxPerL;
            yPoints[numberOfEdges] = dot.getY() * pxPerL;
            this.diameter = dot.getRadius() * 2 * pxPerL;
            numberOfEdges++;
        }
    }

    public void makeGizmo() {
        Gizmo Gizmo = (Gizmo) entity;
        xPoints = new double[Gizmo.getDots().size()];
        yPoints = new double[Gizmo.getDots().size()];

        IEntity.Type type = Gizmo.getType();

        setGizmoColor(type);

        numberOfEdges = 0;

        for (Dot dot : Gizmo.getDots()) {
            xPoints[numberOfEdges] = dot.getX() * pxPerL;
            yPoints[numberOfEdges] = dot.getY() * pxPerL;
            this.diameter = dot.getRadius() * 2 * pxPerL;
            numberOfEdges++;
        }
    }


    private void setGizmoColor(IEntity.Type type) {

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

        this.color = fill;
    }

    public int getNumberOfEdges() {
        return numberOfEdges;
    }

    public IEntity getEntity() {
        return entity;
    }

    public double getDiameter() {
        return diameter;
    }

    public double[] getxPoints() {
        return xPoints;
    }

    public double[] getyPoints() {
        return yPoints;
    }

    public Paint getFill() {
        return color;
    }
}
