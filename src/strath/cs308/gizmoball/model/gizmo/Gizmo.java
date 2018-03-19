package strath.cs308.gizmoball.model.gizmo;

import mit.physics.*;
import strath.cs308.gizmoball.controller.GameLoader;
import strath.cs308.gizmoball.model.Dot;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Pattern;

public abstract class Gizmo implements IGizmo {

    protected final String id;
    protected int rotateCount = 0;
    protected List<Circle> circles;
    protected Set<LineSegment> lines;
    protected Vect rotatingPoint;
    protected double x1;
    protected double y1;
    protected double x2;
    protected double y2;
    private int scoreValue = 0;
    private double reflectionCoefficient = 1;
    private String color = "#ffffff";

    public Gizmo(double x1, double y1, double x2, double y2, String id) {
        setup(x1, y1, x2, y2);
        this.id = id;
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
        rotatingPoint = new Vect(x1 + ((x2 - x1) / 2.0), y1 + ((y2 - y1) / 2.0));
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
        int rotateCount = this.rotateCount;
        for (int i = 0; i < rotateCount; i++) {
            rotate();
        }
        this.rotateCount = rotateCount;
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
        return scoreValue;
    }

    public void setScoreValue(int score) {
        scoreValue = score;
    }

    @Override
    public String toString() {
        String x2String = "";
        String y2String = "";
        StringBuilder rotate = new StringBuilder("\n");
        if (getType().equals(Type.ABSORBER)) {
            x2String = " " + x2;
            y2String = " " + y2;
        }

        for (int i = 0; i < rotateCount % 4; i++) {
            rotate.append(GameLoader.ROTATE_COMMAND + " ").append(id).append("\n");
        }

        return getType() + " " + id + " " + x1 + " " + y1 + x2String + y2String + rotate;
    }

    @Override
    public double getReflectionCoefficient() {
        return reflectionCoefficient;
    }

    @Override
    public void setReflectionCoefficient(double coefficient) {
        reflectionCoefficient = coefficient;
    }

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public boolean setColor(String color) {
        color = color.toLowerCase();
        Pattern pattern = Pattern.compile("#[0-9a-f]]{6}");
        if (pattern.matcher(color).matches()) {
            return false;
        }

        this.color = color;
        return true;
    }

    public boolean overlapsWithGizmo(Gizmo g) {
        if (this.equals(g)) {
            return false;
        }
        if (g.getType().equals(Type.WALLS)) {
            return false;
        }
        return x1 < g.x2 && x2 > g.x1 && y1 < g.y2 && y2 > g.y1;
    }

    public boolean overlapsWithAnyGizmos(Collection<Gizmo> gizmos) {
        return gizmos
                .stream()
                .anyMatch(this::overlapsWithGizmo);
    }
}
