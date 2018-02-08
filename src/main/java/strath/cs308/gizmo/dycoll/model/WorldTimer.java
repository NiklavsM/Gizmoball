package strath.cs308.gizmo.dycoll.model;

import java.util.Timer;
import java.util.TimerTask;

public class WorldTimer implements IWorldTimer
{
    private IPhysicalWorld world;
    private Timer timer;
    private TimerTask task;

    private boolean isRunning;


    public WorldTimer(IPhysicalWorld world)
    {
        this.world = world;


        this.isRunning = false;

    }

    private void start()
    {
        this.isRunning = true;

        this.task = new TimerTask()
        {
            @Override
            public void run()
            {
                world.tick( 1/60.0);
            }
        };

        this.timer = new Timer();
        this.timer.schedule(this.task, 0, 1000/60);

    }

    private void stop()
    {
        this.isRunning = false;

        this.timer.cancel();
        this.timer.purge();
    }

    @Override
    public void toggle()
    {

        if (this.isRunning)
            this.stop();
        else
            this.start();
    }

    public boolean isRunning()
    {
        return isRunning;
    }
}
