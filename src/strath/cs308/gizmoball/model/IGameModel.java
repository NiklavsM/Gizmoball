package strath.cs308.gizmoball.model;

import strath.cs308.gizmoball.model.gizmo.IGizmo;

import java.io.FileNotFoundException;
import java.util.Observer;
import java.util.Set;

public interface IGameModel {

    Set<IGizmo> getGizmos();

    IGizmo getGizmo(double x, double y);

    void addGizmo(IGizmo gizmo);

    boolean remove(String id);

    void tick(double time);

    void addObserver(Observer o);

    Ball getBall(); // Could get rid of this if make ball implement IGizmo

    void shootOut();

    void rotate(String id);

    void clear();
}
