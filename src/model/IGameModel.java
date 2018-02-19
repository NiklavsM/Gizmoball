package model;

import java.util.Observer;
import java.util.Set;

import model.gizmo.IEntity;

public interface IGameModel {

    void reset();

    Set<model.gizmo.IEntity> getEntities();

    boolean remove(String id);

    void startTimer();

    void stopTimer();

    boolean isTimerRunning();

    void moveBall();

    void addObserver(Observer o);

    Ball getBall(); // Could get rid of this if makeGizmo ball implement IEntity

    void shootOut();

    void rotate(String id);

    boolean isOccupied(int squareX, int squareY);

    IEntity getGizmo(int squareX, int squareY);

    void addEntity(IEntity entity);

}
