package model;

import mit.physics.Circle;
import mit.physics.LineSegment;

import java.util.Set;

public interface IGizmo {

    Set<LineSegment> getLines();

    Set<Circle> getCircles();

    Gizmo.Type getType();

}
