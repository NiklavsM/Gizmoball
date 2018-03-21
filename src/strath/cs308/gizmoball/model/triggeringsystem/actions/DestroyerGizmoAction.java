package strath.cs308.gizmoball.model.triggeringsystem.actions;

import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.gizmo.IGizmo;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class DestroyerGizmoAction extends AbstractGizmoAction {
    private final List<IGizmo> gizmos;

    public DestroyerGizmoAction(IGameModel gameModel) {
        super(gameModel, null);
        gizmos = new LinkedList<>(gameModel.getGizmos());
    }

    @Override
    public void doAction(Object args) {
        Collections.shuffle(gizmos);
        gameModel.removeGizmo(gizmos.remove(0));
    }
}
