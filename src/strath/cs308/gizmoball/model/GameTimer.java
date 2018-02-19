package strath.cs308.gizmoball.model;

import java.util.Timer;
import java.util.TimerTask;

public class GameTimer implements IGameTimer {

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

            TimerTask task = new TimerTask()
            {
                @Override
                public void run()
                {
                    gameModel.tick(1 / refreshRate);
                }
            };


            this.timer = new Timer();
            this.timer.schedule(task, 0, (long) (1000 / refreshRate));
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
