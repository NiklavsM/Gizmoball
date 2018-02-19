package model;

import physics.Circle;
import physics.LineSegment;

import java.util.List;
import java.util.Set;

public interface IGizmo {

    Set<LineSegment> getLines();

    List<Circle> getCircles();

    String getId();

    void rotate();

    double getXLocation();

    double getYLocation();
}