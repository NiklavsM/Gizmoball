package strath.cs308.gizmoball.model;

import mit.physics.Vect;
import strath.cs308.gizmoball.model.gizmo.Gizmo;


public class CollisionDetails {
    private double tuc;
    private Vect velo;
    private Gizmo gizmo;

    public CollisionDetails(double t, Vect v, Gizmo gizmo) {
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

    public Gizmo getGizmo() {
        return gizmo;
    }

}
