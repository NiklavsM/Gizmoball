package strath.cs308.gizmoball.model.triggeringsystem.actions;

import strath.cs308.gizmoball.controller.GameLoader;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.UndoRedo;
import strath.cs308.gizmoball.model.gizmo.Ball;
import strath.cs308.gizmoball.model.gizmo.IGizmo;
import strath.cs308.gizmoball.model.triggeringsystem.DefaultTriggarable;
import strath.cs308.gizmoball.model.triggeringsystem.ITrigger;
import strath.cs308.gizmoball.model.triggeringsystem.ITriggerable;
import strath.cs308.gizmoball.utils.Logger;

import java.util.Timer;
import java.util.TimerTask;

public class GoToJailAction extends AbstractGizmoAction {
    private final String jailFilePath;
    private final GameLoader gameLoader;
    private final Timer timer;

    public GoToJailAction(IGameModel gameModel) {
        super(gameModel, null);
        this.gameLoader = new GameLoader(gameModel);
        jailFilePath = "/jail.gizmo";
        timer = new Timer();
    }

    @Override
    public void doAction(Object args) {
        UndoRedo.INSTANCE.saveState(gameModel);
        gameModel.reset();
        try {
            gameLoader.load(getClass().getResourceAsStream(jailFilePath));
        } catch (Exception e) {
            Logger.error("JAIL", "failed to load");
        }

        removeFan(6);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                gameModel.getGizmos()
                        .stream()
                        .filter(g -> g.getStartY() == 8 && g.getType() == IGizmo.Type.SQUARE)
                        .forEach(g -> gameModel.removeGizmo(g));
            }
        }, 5000);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                gameModel.getGizmos().stream()
                        .filter(g -> g.getType() == IGizmo.Type.SQUARE)
                        .forEach(g -> g.setColor("#00FF00"));
            }
        }, 5500);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                ITriggerable l1 = (ITriggerable) gameModel.getGizmoById("l1");
                ITriggerable r2 = (ITriggerable) gameModel.getGizmoById("r2");
                l1.performAction("trigger");
                r2.performAction("trigger");
            }
        }, 6000);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                ITriggerable l2 = (ITriggerable) gameModel.getGizmoById("l2");
                ITriggerable r1 = (ITriggerable) gameModel.getGizmoById("r1");
                l2.performAction("trigger");
                r1.performAction("trigger");
            }
        }, 6500);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                gameModel.getGizmos().stream()
                        .filter(g -> g.getType() == IGizmo.Type.LEFT_FLIPPER ||
                                g.getType() == IGizmo.Type.RIGHT_FLIPPER)
                        .map(ITriggerable.class::cast)
                        .forEach(t -> t.performAction("trigger"));
            }
        }, 9500);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //fixme using ball
                Ball b = (Ball) gameModel.getGizmoById("B");
                DefaultTriggarable triggarable = new DefaultTriggarable();
                triggarable.setAction(args -> UndoRedo.INSTANCE.undo(gameModel));
                ITrigger walls = (ITrigger) gameModel.getGizmoById("OuterWalls");
                walls.registerTriggerable(triggarable);
                b.setVelocity(0, -3);
            }
        }, 7000);
    }

    private void removeFan(int n) {
        if (n == 0) {
            return;
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                gameModel.removeGizmo("S" + n);
                removeFan(n - 1);
            }
        }, 500);
    }
}
