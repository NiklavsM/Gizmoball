package strath.cs308.gizmoball.model;

import strath.cs308.gizmoball.controller.Constants;
import strath.cs308.gizmoball.utils.Logger;

import java.util.Timer;
import java.util.TimerTask;

public class GameTimer implements IGameTimer {

    private static final String TAG = "GameTimer";
    private IGameModel gameModel;
    private Timer timer;

    private boolean isRunning;
    private Double refreshRate;

    public GameTimer(IGameModel gameModel) {
        this(gameModel, 60.0);
    }

    public GameTimer(IGameModel gameModel, Double refreshRate) {
        this.gameModel = gameModel;
        this.refreshRate = refreshRate;
        isRunning = false;
    }

    public void start() {
        if (!isRunning) {
            this.isRunning = true;

            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    gameModel.tick(Constants.TICK_TIME);
                }
            };


            this.timer = new Timer();
            Logger.verbose(TAG,"time: " + (Constants.TICK_TIME * 1000));
            this.timer.schedule(task, 0, (long) (Constants.TICK_TIME * 1000));
        }
    }

    public void stop() {
        if (isRunning) {
            this.isRunning = false;

            this.timer.cancel();
            this.timer.purge();
        }
    }

    public boolean isRunning() {
        return isRunning;
    }
}
