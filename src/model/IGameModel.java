package model;

import java.util.Observer;
import java.util.Set;

import model.gizmo.IGizmo;

public interface IGameModel {
    Set<IGizmo> getGizmos();

    void addGizmo(IGizmo gizmo);

    void startTimer();

    void stopTimer();

    boolean isTimerRunning();

    void moveBall();

    void addObserver(Observer o);

    Ball getBall(); // Could get rid of this if make ball implement IGizmo
    
    void shootOut();

}
