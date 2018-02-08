package model;

import java.util.Set;

import physics.Circle;
import physics.LineSegment;

public interface IGizmo {

    Set<LineSegment> getLines();

    Set<Circle> getCircles();

}
