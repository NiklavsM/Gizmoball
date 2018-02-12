package model;

import physics.Vect;

/**
 * @author Murray Wood Demonstration of MVC and MIT Physics Collisions 2014
 */

public class CollisionDetails {
    private double tuc;
    private Vect velo;
    private IGizmo gizmo;

    public CollisionDetails(double t, Vect v, IGizmo gizmo) {
        this.gizmo = gizmo;
        tuc = t;
        velo = v;
    }

    public double getTuc() {
        return tuc;
    }

    public Vect getVelo() {
        return velo;
    }

    public IGizmo getGizmo(){
        return gizmo;
    }

}
