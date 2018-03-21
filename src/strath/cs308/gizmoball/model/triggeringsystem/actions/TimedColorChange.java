package strath.cs308.gizmoball.model.triggeringsystem.actions;

import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.gizmo.IGizmo;

import java.util.Timer;
import java.util.TimerTask;


public class TimedColorChange extends AbstractGizmoAction {

    private final String color;
    private final String gizmoColor;
    private final Timer timer;
    private final long timeout;
    private boolean waiting;

    public TimedColorChange(IGameModel gameModel, IGizmo gizmo, String color, long timeout) {
        super(gameModel, gizmo);
        this.color = color;
        gizmoColor = gizmo.getColor();
        this.timeout = timeout;
        timer = new Timer();
        waiting = false;
    }

    @Override
    public void doAction(Object args) {
        if (waiting) {
            return;
        }
        gameModel.setGizmoColor(gizmo, color);
        waiting = true;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                gameModel.setGizmoColor(gizmo, gizmoColor);
                waiting = false;
            }
        }, timeout);
    }
}
