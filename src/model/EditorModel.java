package model;

import gui.GizmoView;
import model.gizmo.IEntity;

import java.util.*;

public class EditorModel extends Observable {

    public enum Mode {
        ADD, SELECT, CONNECT, ROTATE
    }

    private final GizmoView gizmoView;
    private IGameModel gameModel;
    private Mode selectedAction;
    private List<IEntity> selectedGizmos;
    private Stack<IGameModel> redoStack;
    private Stack<IGameModel> undoStack;
    private IEntity.Type gizmoGridItem;
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


    public IEntity.Type getGizmoGridItem() {
        return gizmoGridItem;
    }

    public void setGizmoGridItem(IEntity.Type gizmoGridItem) {
        this.gizmoGridItem = gizmoGridItem;
        setChanged();
        notifyObservers();
    }

    public IEntity.Type selectedGizmoGridItem() {
        return gizmoGridItem;
    }

    public Mode getSelectedAction() {
        return selectedAction;
    }

    public void setMode(Mode selectedAction) {
        this.selectedAction = selectedAction;
        setChanged();
        notifyObservers();
    }

    public List<IEntity> getSelectedGizmos() {
        return selectedGizmos;
    }

    public void setSelectedGizmos(List<model.gizmo.IEntity> selectedGizmos) {
        this.selectedGizmos = selectedGizmos;
        setChanged();
        notifyObservers();
    }
}
