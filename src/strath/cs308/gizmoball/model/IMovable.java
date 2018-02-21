package strath.cs308.gizmoball.model;

import mit.physics.Vect;

public interface IMovable {

    void move(double timeInSeconds);

    void setVelocity(Vect velocity);

    Vect getVelocity();

}
