package strath.cs308.gizmoball.model;

import strath.cs308.gizmoball.model.gizmo.IGizmo;

import java.io.FileNotFoundException;
import java.util.Observer;
import java.util.Set;

public interface IGameModel {

    static IGameModel loadGame(String path) throws FileNotFoundException, IllegalAccessException {
        GameLoader gameLoader = new GameLoader(path);
        return gameLoader.load();
    }

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

}
