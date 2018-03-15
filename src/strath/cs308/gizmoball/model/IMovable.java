package strath.cs308.gizmoball.model;

import mit.physics.Vect;

public interface IMovable {

    public enum Type {
        LINEAR, ROTATION;
    }

    void move(double timeInSeconds);

    Vect getVelocity();

    void setVelocity(Vect velocity);

    Type getMovementType();
}
