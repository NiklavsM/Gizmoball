package model;

import physics.Vect;

public interface IMovable {

    void move(double time);

    Vect getVelocity();

    void setVelocity(Vect velocity);

}
