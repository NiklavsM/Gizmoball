package strath.cs308.gizmoball.controller;

import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.gizmo.IGizmo;

import java.util.Stack;

public class BoardHistory {
    private static Stack<GizmoActionPair> history = new Stack<>();
    private static Stack<GizmoActionPair> undoHistory = new Stack<>();

    public static void addToHistoryGizmoRemoved(IGizmo gizmo) {

        history.add(new GizmoActionPair(gizmo, HistoryAction.GIZMO_REMOVED));
        undoHistory.removeAllElements();
        System.out.println("historyAdded " + history.size());
    }
    public static void addToHistoryGizmoAdded(IGizmo gizmo) {

        history.add(new GizmoActionPair(gizmo, HistoryAction.GIZMO_ADDED));
        undoHistory.removeAllElements();
        System.out.println("historyAdded " + history.size());
    }


    static void popFromHistory(IGameModel gameModel) {

        if (!history.isEmpty()) {
            GizmoActionPair previousState = history.pop();
            HistoryAction action = previousState.getAction();
            if (action.equals(HistoryAction.GIZMO_ADDED)) {
                gameModel.removeGizmo(previousState.getIGizmo().getId());
            }
            if (action.equals(HistoryAction.GIZMO_REMOVED)) {
                gameModel.addGizmo(previousState.getIGizmo());
            }
            undoHistory.add(previousState);
        }
    }

    static void popFromUndoHistory(IGameModel gameModel) {

        if (!undoHistory.isEmpty()) {
            GizmoActionPair previousState = undoHistory.pop();
            HistoryAction action = previousState.getAction();
            if (action.equals(HistoryAction.GIZMO_ADDED)) {
                gameModel.addGizmo(previousState.getIGizmo());
            }
            if (action.equals(HistoryAction.GIZMO_REMOVED)) {

                gameModel.removeGizmo(previousState.getIGizmo().getId());
            }
            history.add(previousState);
        }
    }

    public enum HistoryAction {
        GIZMO_ADDED, GIZMO_REMOVED;
    }

}
