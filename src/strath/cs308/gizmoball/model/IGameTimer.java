package strath.cs308.gizmoball.model;

public interface IGameTimer {

    double DEFAULT_TICK_RATE = 1.0 / 60.0;

    void start();

    void stop();

    boolean isRunning();
}
