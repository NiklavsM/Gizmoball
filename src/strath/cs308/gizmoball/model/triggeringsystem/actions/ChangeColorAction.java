package strath.cs308.gizmoball.model.triggeringsystem.actions;


import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.gizmo.IGizmo;
import strath.cs308.gizmoball.utils.Logger;

public class ChangeColorAction extends ChangeToARandomColor {

    public ChangeColorAction(IGameModel gameModel, IGizmo gizmo, String color) {
        super(gameModel, gizmo, color);
    }

    @Override
    public void doAction(Object args) {
        Logger.verbose("ChangeC", "in here");
        String gizmoColor = gizmo.getColor();
        gameModel.setGizmoColor(gizmo, colors[0]);
        colors[0] = gizmoColor;
    }
}
