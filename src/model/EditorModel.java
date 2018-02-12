package model;

import gui.GizmoView;
import model.gizmo.IGizmo;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class EditorModel {

    public enum EditorMode {
        ADD, SELECT, CONNECT, ROTATE
    }

    private final GizmoView gizmoView;
    private IGameModel gameModel;
    private EditorMode selectedAction;
    private List<IGizmo> selectedGizmos;
    private Stack<IGameModel> redoStack;
    private Stack<IGameModel> undoStack;

    public EditorModel(GizmoView gizmoView) {
        this.gizmoView = gizmoView;
        this.gameModel = gizmoView.getGameModel();

        this.selectedAction = EditorMode.ADD;

        this.redoStack = new Stack<>();
        this.undoStack = new Stack<>();

        this.selectedGizmos = new ArrayList<>();
    }

    public void undo() {
        redoStack.push(gameModel);
        gizmoView.setGameModel(undoStack.pop());
    }

    public void redo() {
        undoStack.push(gameModel);
        gameModel = redoStack.pop();
    }


    public EditorMode getSelectedAction() {
        return selectedAction;
    }

    public void setSelectedAction(EditorMode selectedAction) {
        this.selectedAction = selectedAction;
    }

    public List<IGizmo> getSelectedGizmos() {
        return selectedGizmos;
    }

    public void setSelectedGizmos(List<IGizmo> selectedGizmos) {
        this.selectedGizmos = selectedGizmos;
    }
}
