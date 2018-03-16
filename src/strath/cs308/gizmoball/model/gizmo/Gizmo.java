package strath.cs308.gizmoball.model.gizmo;

import mit.physics.*;
import strath.cs308.gizmoball.controller.GameLoader;
import strath.cs308.gizmoball.model.Dot;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Gizmo implements IGizmo {

    protected final String id;
    protected int rotateCount;
    protected List<Circle> circles;
    protected Set<LineSegment> lines;
    protected Vect rotatingPoint;
    protected double x1;
    protected double y1;
    protected double x2;
    protected double y2;
    private int scoreValue;

    public Gizmo(double x1, double y1, double x2, double y2, String id) {
        setup(x1, y1, x2, y2);
        this.id = id;
        rotateCount = 0;
        rotatingPoint = new Vect(x1 + ((x2 - x1) / 2.0), y1 + ((y2 - y1) / 2.0));
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
        List<Dot> dots = new CopyOnWriteArrayList<>();
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

    protected void setup(double x1, double y1, double x2, double y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.circles = new CopyOnWriteArrayList<>();
        this.lines = new HashSet<>();
    }

    public void rotate() {
        rotateCount++;
        Set<LineSegment> newLineSet = new HashSet<>();
        lines.forEach(line -> {
            line = Geometry.rotateAround(line, rotatingPoint, Angle.DEG_90);
            newLineSet.add(line);
        });
        lines = newLineSet;

        List<Circle> newCircleList = new CopyOnWriteArrayList<>();
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
    public void move(double x, double y) {
        setup(x, y, x2 - x1 + x, y2 - y1 + y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Gizmo gizmo = (Gizmo) o;

        if (rotateCount != gizmo.rotateCount) return false;
        if (Double.compare(gizmo.x1, x1) != 0) return false;
        if (Double.compare(gizmo.y1, y1) != 0) return false;
        if (Double.compare(gizmo.x2, x2) != 0) return false;
        if (Double.compare(gizmo.y2, y2) != 0) return false;
        if (scoreValue != gizmo.scoreValue) return false;
        if (!id.equals(gizmo.id)) return false;
        return rotatingPoint != null ? rotatingPoint.equals(gizmo.rotatingPoint) : gizmo.rotatingPoint == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id.hashCode();
        result = 31 * result + rotateCount;
        result = 31 * result + (rotatingPoint != null ? rotatingPoint.hashCode() : 0);
        temp = Double.doubleToLongBits(x1);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y1);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(x2);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y2);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + scoreValue;
        return result;
    }

    public int getScoreValue() {
        switch (this.getType()) {
            // no points are granted for flipper collisions, ball-to-ball collisions or walls collisions
            case RIGHT_FLIPPER:
            case LEFT_FLIPPER:
            case BALL:
            case WALLS:
                return 0;
            // deduct 10 points when ball is absorbed
            case ABSORBER:
                return -20;
            // grant 1 point if any gizmo is hit apart from the absorber
            default:
                return 1;
        }
    }

    @Override
    public String toString() {
        if (getType().equals(Type.WALLS)) {
            return "";
        }

        String x2String = "", y2String = "", rotate = "";
        if (getType().equals(Type.ABSORBER)) {
            x2String = " " + x2;
            y2String = " " + y2;
        }

        for (int i = 0; i < rotateCount % 4; i++) {
            rotate += GameLoader.ROTATE_COMMAND + " " + id + "\n";
        }

        return getType() + " " + id + " " + x1 + " " + y1 + x2String + y2String + "\n" + rotate + "\n";
    }
}
