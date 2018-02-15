package strath.cs308.gizmoball.model;

import mit.physics.*;

import java.sql.Time;
import java.util.*;

public class Shape {

    protected List<Circle> circles;
    protected Set<LineSegment> lines;
    protected int rotateCount;
    protected Vect rotatingPoint;

    public Shape() {
        circles = new ArrayList<>();
        lines = new HashSet<>();

        rotateCount = 0;
    }

    public void addLine(LineSegment lineSegment) {
        lines.add(lineSegment);
    }


    public void removeLine(LineSegment lineSegment) {
        lines.remove(lineSegment);
    }

    public void addLine(double x1, double y1, double x2, double y2) {
        lines.add(new LineSegment(x1, y1, x2, y2));
    }

    public void addCorner(double x1, double y1) {
        circles.add(new Circle(x1, y1, 0));
    }

    public void addCorner(Circle circle) {
        circles.add(circle);
    }

    public void removeCorner(Circle circle) {
        circles.remove(circle);
    }

    public List<Dot> getDots() {
        List<Dot> dots = new LinkedList<>();
        circles.forEach(circle -> dots.add(new Dot(circle.getCenter().x(),
                circle.getCenter().y(),
                circle.getRadius())));
        return dots;
    }

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

    public List<Circle> getCircles() {
        return circles;
    }

    public Set<LineSegment> getLines() {
        return lines;
    }

    public int getRotateCount() {
        return rotateCount;
    }

    public Vect getRotatingPoint() {
        return rotatingPoint;
    }

    public void setRotatingPoint(Vect rotatingPoint) {
        this.rotatingPoint = rotatingPoint;
    }

    public void move(Time time) {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shape shape = (Shape) o;
        return rotateCount == shape.rotateCount &&
                Objects.equals(circles, shape.circles) &&
                Objects.equals(lines, shape.lines) &&
                Objects.equals(rotatingPoint, shape.rotatingPoint);
    }

    @Override
    public int hashCode() {

        return Objects.hash(circles, lines, rotateCount, rotatingPoint);
    }


}
