package strath.cs308.gizmoball.controller;

import strath.cs308.gizmoball.GizmoBall;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.gizmo.IGizmo;

import javax.swing.plaf.basic.BasicTreeUI;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Stack;

public class History {
    private static Stack<IGameModel> history = new Stack<>();
    private static Stack<IGameModel> undoHistory = new Stack<>();
    private static GizmoBall gizmoBall;

    public static void addToHistory(IGameModel gameModel) {
        String s = gameModel.toString() + "\n" ;//+ gizmoBall.getKeyHandler().toString();
        try {
            history.add(gizmoBall.getGameLoader().loadIntoNewModel(new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8))));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        undoHistory.removeAllElements();
        System.out.println("historyAdded " + history.size());
    }

    public static void init(GizmoBall gizmoBallNew) {
        gizmoBall = gizmoBallNew;
    }

//    public static void addToHistoryGizmoAdded(IGameModel gameModel) {
//
//        history.add(gameModel);
//        undoHistory.removeAllElements();
//        System.out.println("historyAdded " + history.size());
//    }


    static IGameModel popFromHistory() {
        System.out.println("historyPopped " + history.size());
        if (!history.isEmpty()) {
            IGameModel previousState = history.pop();
            undoHistory.add(previousState.deepCopy(previousState));
            return previousState;
        }
        return null; //TODO
    }

    static IGameModel popFromUndoHistory() {
        System.out.println("undoHistoryPopped " + undoHistory.size());
        if (!undoHistory.isEmpty()) {

            IGameModel previousState = undoHistory.pop();
            undoHistory.add(previousState.deepCopy(previousState));
            return previousState;

        }
        return null; //TODO
    }

//    public enum HistoryAction {
//        GIZMO_ADDED, GIZMO_REMOVED;
//    }

}
