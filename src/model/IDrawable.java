package model;

import physics.Circle;
import physics.LineSegment;

import java.util.List;
import java.util.Set;

public interface IDrawable {

    Set<LineSegment> getLines();
    List<Circle> getCircles();
}
