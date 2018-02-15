package strath.cs308.gizmoball.model;

import mit.physics.LineSegment;
import mit.physics.Vect;

public class Line {

    LineSegment line;

    public Line(double x1, double y1, double x2, double y2) {
        line = new LineSegment(x1, y1, x2, y2);
    }

    public Vect getP1() {
        return line.p1();
    }

    public Vect getP2() {
        return line.p2();
    }
}
