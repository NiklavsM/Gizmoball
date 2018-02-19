package model;

import model.gizmo.IEntity;
import physics.Vect;

/**
 * @author Murray Wood Demonstration of MVC and MIT Physics Collisions 2014
 */

public class CollisionDetails {
    private double tuc;
    private Vect velo;
    private IEntity entity;

    public CollisionDetails(double t, Vect v, IEntity entity) {
        this.entity = entity;
        tuc = t;
        velo = v;
    }

    public double getTuc() {
        return tuc;
    }

    public Vect getVelo() {
        return velo;
    }

    public IEntity getEntity(){
        return entity;
    }

}
