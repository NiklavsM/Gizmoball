package model.gizmo;

import model.Dot;
import physics.*;

import java.util.*;

public abstract class Gizmo implements IGizmo {

    protected final String id;
    protected int rotateCount;
    protected List<Circle> circles;
    protected Set<LineSegment> lines;
    protected Vect rotatingPoint;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Gizmo gizmo = (Gizmo) o;

        if (rotateCount != gizmo.rotateCount) return false;
        if (!id.equals(gizmo.id)) return false;
        if (!circles.equals(gizmo.circles)) return false;
        return lines.equals(gizmo.lines);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + rotateCount;
        result = 31 * result + circles.hashCode();
        result = 31 * result + lines.hashCode();
        return result;
    }

    public Gizmo(String id) {
        this.circles = new LinkedList<>();
        this.lines = new HashSet<>();
        this.id = id;
        rotateCount = 0;
    }

    public Gizmo() {
        this(generateID());
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

    public Set<LineSegment> getLines() {
        return lines;
    }

    protected static String generateID() {
        return UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public abstract Type getType();

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

}
