package strath.cs308.gizmoball.model;

import mit.physics.Circle;

public interface IMovable {

    boolean isMoving();

    Double getCurrentRadianVelocity();

    Circle getSpinAround();

    void move(double timeInSeconds);

    void setVelocity(double x, double y);

    double getVelocityX();

    double getVelocityY();

    double getVelocityRadian();

    void setVelocityRadian(double radian);

    Type getMovementType();

    enum Type {
        LINEAR, ROTATION;
    }
}
