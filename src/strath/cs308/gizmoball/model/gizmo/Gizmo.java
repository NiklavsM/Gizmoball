package strath.cs308.gizmoball.model.gizmo;

import mit.physics.*;
import strath.cs308.gizmoball.model.Dot;

import java.util.*;

public abstract class Gizmo implements IGizmo {

    protected final String id;
    protected int rotateCount;
    protected List<Circle> circles;
    protected Set<LineSegment> lines;
    protected Vect rotatingPoint;
    private double x1;
    private double y1;
    private double x2;
    private double y2;
    private int scoreValue;

    public Gizmo(double x1, double y1, double x2, double y2, String id) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.circles = new LinkedList<>();
        this.lines = new HashSet<>();
        this.id = id;
        rotateCount = 0;

        setup(x1, y1, x2, y2);
    }

    public Gizmo(double x1, double y1, double x2, double y2) {
        this(x1, y1, x2, y2, generateID());
    }

    protected static String generateID() {
        return UUID.randomUUID().toString();
    }

    public List<Circle> getCircles() {
        return circles;
    }

    public List<Dot> getDots() {
        List<Dot> dots = new LinkedList<>();
        circles.forEach(circle -> dots.add(new Dot(circle.getCenter().x(),
                circle.getCenter().y(),
                circle.getRadius())));
        return dots;
    }

    @Override
    public Set<LineSegment> getLines() {
        return lines;
    }

    @Override
    public abstract IGizmo.Type getType();

    public String getId() {
        return id;
    }

    protected abstract void setup(double x1, double y1, double x2, double y2);

    public void rotate() {
        rotateCount++;
        Set<LineSegment> newLineList = new HashSet<>();
        lines.forEach(line -> {
            line = Geometry.rotateAround(line, rotatingPoint, Angle.DEG_90);
            newLineList.add(line);
        });
        lines = newLineList;

        List<Circle> newCircleList = new LinkedList<>();
        circles.forEach(circle -> {
            circle = Geometry.rotateAround(circle, rotatingPoint, Angle.DEG_90);
            newCircleList.add(circle);
        });
        circles = newCircleList;
    }

    public double getStartX() {
        return x1;
    }

    public double getStartY() {
        return y1;
    }

    public double getEndX() {
        return x2;
    }

    public double getEndY() {
        return y2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Gizmo Gizmo = (Gizmo) o;

        if (rotateCount != Gizmo.rotateCount) return false;
        if (!id.equals(Gizmo.id)) return false;
        if (!circles.equals(Gizmo.circles)) return false;
        return lines.equals(Gizmo.lines);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + rotateCount;
        result = 31 * result + circles.hashCode();
        result = 31 * result + lines.hashCode();
        return result;
    }


    public int getScoreValue() {
        return 1;
    }
}
