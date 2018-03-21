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

    boolean isGizmoAddable(IGizmo gizmo);

    boolean removeGizmo(String id);

    boolean removeGizmo(IGizmo gizmo);

    boolean setGizmoColor(IGizmo gizmo, String color);

    void tick(double time);

    boolean move(IGizmo gizmo, double x, double y);

    void addObserver(Observer o);

    void deleteObserver(Observer observer);

    boolean rotate(String id);

    double getFrictionM1();

    double getFrictionM2();

    boolean setFrictionM2(double frictionCoefficient);

    boolean setFrictionM1(double frictionCoefficient);

    double getGravityCoefficient();

    boolean setGravityCoefficient(double gravityCoefficient);

    void reset();

    void update();

    int getScore();

    int[] getStatistics();
}

