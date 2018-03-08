package strath.cs308.gizmoball.model;

import strath.cs308.gizmoball.model.gizmo.IGizmo;

import java.util.Observer;
import java.util.Optional;
import java.util.Set;

public interface IGameModel {

    Set<IGizmo> getGizmos();

    Optional<IGizmo> getGizmo(double x, double y);

    boolean addGizmo(IGizmo gizmo);

    boolean remove(String id);

    void tick(double time);

    void addObserver(Observer o);

    void rotate(String id);

    void reset();

    int getScore();
}
