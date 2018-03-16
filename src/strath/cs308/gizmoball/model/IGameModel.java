package strath.cs308.gizmoball.model;

import strath.cs308.gizmoball.model.gizmo.IGizmo;

import java.util.Observer;
import java.util.Optional;
import java.util.Set;

public interface IGameModel {

    Set<IGizmo> getGizmos();

    Set<IGizmo> getGizmoBalls();

    Optional<IGizmo> getGizmo(double x, double y);

    IGizmo getGizmoById(String id);

    boolean addGizmo(IGizmo gizmo);

    void deleteObservers();

    boolean removeGizmo(String id);

    boolean removeGizmo(IGizmo gizmo);

    void tick(double time);

    void addObserver(Observer o);

    void rotate(String id);

    double setFrictionM1();

    double getFrictionM2();

    boolean setFrictionM2(double frictionCoefficient);

    boolean setFrictionM1(double frictionCoefficient);

    boolean getFrictionM2(double frictionCoefficient);

    double getGravityCoefficient();

    boolean setGravityCoefficient(double gravityCoefficient);

    void reset();

    void update();

    IGameModel deepCopy(IGameModel gameModel);

    int getScore();

}

