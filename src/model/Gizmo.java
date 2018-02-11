package model;

import java.util.HashSet;
import java.util.Set;

public abstract class Gizmo implements IGizmo {
    private final String id;
    protected Set<Circle> circles;
    protected Set<Line> lines;

    public Gizmo(String id) {
        this.circles = new HashSet<>();
        this.lines = new HashSet<>();
        this.id = id;
    }

    public Set<Circle> getCircles() {
        return circles;
    }

    public Set<Line> getLines() {
        return lines;
    }

    public Circle getRootCircle() {
        double xMin = 20, yMin = 20;
        Circle rootCircle = null;
        for (Circle circle : circles) {
            if (circle.getX() <= xMin) {
                if (circle.getY() <= yMin) {
                    xMin = circle.getX();
                    yMin = circle.getY();
                    rootCircle = circle;
                }
            }
        }
        return rootCircle;
    }

    public Circle getEndCircle() {
        double xMax = 0, yMax = 0;
        Circle endCircle = null;
        for (Circle circle : circles) {
            if (circle.getX() >= xMax) {
                if (circle.getY() >= yMax) {
                    xMax = circle.getX();
                    yMax = circle.getY();
                    endCircle = circle;
                }
            }
        }
        return endCircle;
    }


    public abstract Type getType();


}
