package model;

import java.io.FileNotFoundException;
import java.util.Observer;
import java.util.Set;

import controller.GameLoader;
import model.gizmo.IGizmo;

public interface IGameModel {

    void reset();

    Set<IGizmo> getGizmos();

    void addGizmo(IGizmo gizmo);

    boolean remove(String id);

    void startTimer();

    void stopTimer();

    boolean isTimerRunning();

    void moveBall();

    void addObserver(Observer o);

    Ball getBall(); // Could get rid of this if make ball implement IGizmo

    void shootOut();

    void rotate(String id);

    boolean isOccupied(int squareX, int squareY);

    IGizmo getGizmo(int squareX, int squareY);
}
