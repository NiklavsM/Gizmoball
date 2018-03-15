package strath.cs308.gizmoball.model;

import strath.cs308.gizmoball.model.gizmo.IGizmo;
import strath.cs308.gizmoball.model.triggeringsystem.ITrigger;

import java.util.Observer;
import java.util.Optional;
import java.util.Set;

public interface IGameModel {

    Set<IGizmo> getGizmos();

    Set<IGizmo> getGizmoBalls();

    Optional<IGizmo> getGizmo(double x, double y);

    IGizmo getGizmoById(String id);

    boolean addGizmo(IGizmo gizmo);

    boolean removeGizmo(String id);

    void tick(double time);

    void addObserver(Observer o);

    void rotate(String id);

    double getFrictionCoefficient();

    boolean setFrictionCoefficient(double frictionCoefficient);

    double getGravityCoefficient();

    boolean setGravityCoefficient(double gravityCoefficient);

    void reset();

    void update();

    int getScore();

    void addCollisionTrigger(ITrigger from);
}
