package strath.cs308.gizmoball.controller.actions;

import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.gizmo.IGizmo;

public class ChangeToARandomColor extends AbstractGizmoAction {

    protected final String[] colors;

    public ChangeToARandomColor(IGameModel gameModel, IGizmo gizmo, String... colors) {
        super(gameModel, gizmo);
        this.colors = colors;
    }

    @Override
    public void doAction(Object args) {
        gameModel.setGizmoColor(gizmo, colors[(int) (Math.random() * colors.length)]);
    }
}
