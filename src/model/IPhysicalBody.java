package model;

import physics.Circle;
import physics.LineSegment;
import physics.Vect;

import java.util.List;

public interface IPhysicalBody {
    void setVelocity(Vect velocity);

    Vect getVelocity();

    List<LineSegment> getLineSegments();

    List<Circle> getCircles();

    void move(double time);
}
