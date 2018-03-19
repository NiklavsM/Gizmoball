package strath.cs308.gizmoball.model;

public interface IMovable {

    void move(double timeInSeconds);

    void setVelocity(double x, double y);

    double getVelocityX();

    double getVelocityY();

    double getVelocityRadian();

    void setVelocityRadian(double radian);

    Type getMovementType();

    public enum Type {
        LINEAR, ROTATION;
    }
}
