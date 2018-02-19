package strath.cs308.gizmoball.model;

import mit.physics.Vect;

/**
 * Created by xqb16141 on 12/02/18.
 */
public interface IMovable {

    void move(double timeInSeconds);

    void setVelocity(Vect velocity);

    Vect getVelocity();

}
