package model;

import java.util.List;

import physics.Circle;
import physics.LineSegment;
import physics.Vect;

public interface IGizmo {

    List<LineSegment> getLines();

    List<Circle> getCircles();

}
