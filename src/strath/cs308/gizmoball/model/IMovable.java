package strath.cs308.gizmoball.model;

import mit.physics.Vect;

public interface IMovable {

    public enum Type {
        LINEAR, ROTATION;
    }

    void move(double timeInSeconds);

    void setVelocityRadian(double radian);

    void setVelocity(double x, double y);

    double getVelocityX();

    double getVelocityY();

    double getVelocityRadian();

    Type getMovementType();
}
