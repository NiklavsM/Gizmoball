package model;

import gui.GizmoView;
import model.gizmo.IGizmo;

import java.util.*;

public class EditorModel extends Observable {

    public enum Mode {
        ADD, SELECT, CONNECT, ROTATE
    }

    private final GizmoView gizmoView;
    private IGameModel gameModel;
    private Mode selectedAction;
    private List<IGizmo> selectedGizmos;
    private Stack<IGameModel> redoStack;
    private Stack<IGameModel> undoStack;
    private String status;

    public EditorModel(GizmoView gizmoView) {
        this.gizmoView = gizmoView;

        this.gameModel = gizmoView.getGameModel();

        this.selectedAction = Mode.ADD;

        this.redoStack = new Stack<>();
        this.undoStack = new Stack<>();

        this.selectedGizmos = new ArrayList<>();
    }

    public void undo() {
        redoStack.push(gameModel);
        gizmoView.setGameModel(undoStack.pop());
        setChanged();
        notifyObservers();
    }

    public void redo() {
        undoStack.push(gameModel);
        gameModel = redoStack.pop();
        setChanged();
        notifyObservers();
    }


    public Mode getSelectedAction() {
        return selectedAction;
    }

    public void setMode(Mode selectedAction) {
        this.selectedAction = selectedAction;
        setChanged();
        notifyObservers();
    }

    public List<IGizmo> getSelectedGizmos() {
        return selectedGizmos;
    }

    public void setSelectedGizmos(List<IGizmo> selectedGizmos) {
        this.selectedGizmos = selectedGizmos;
        setChanged();
        notifyObservers();
    }
}
