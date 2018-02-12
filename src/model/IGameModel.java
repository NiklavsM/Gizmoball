package model;

import model.gizmo.IGizmo;

import java.util.List;
import java.util.Observer;

public interface IGameModel {
    List<IGizmo> getGizmos();

    void addGizmo(IGizmo gizmo);

    void startTimer();

    void stopTimer();

    boolean isTimerRunning();

    void moveBall();

    void addObserver(Observer o);

    Ball getBall(); // Could get rid of this if make ball implement IGizmo

}
