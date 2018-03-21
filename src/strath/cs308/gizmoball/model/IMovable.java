package strath.cs308.gizmoball.model;

import mit.physics.Circle;
import mit.physics.Vect;

public interface IMovable {

    boolean isMoving();

    Double getCurrentRadianVelocity();

    double getSpinAroundX();

    double getSpinAroundY();

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
