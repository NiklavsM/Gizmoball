package strath.cs308.gizmoball.model;

import strath.cs308.gizmoball.controller.GameLoader;

import java.io.ByteArrayInputStream;
import java.util.Deque;
import java.util.LinkedList;

public enum UndoRedo {
    INSTANCE;

    private Deque<String> undoStates;
    private Deque<String> redoStates;
    private String currentState;

    UndoRedo() {
        undoStates = new LinkedList<>();
        redoStates = new LinkedList<>();
    }


    public void saveState(IGameModel gameModel) {

        String state = gameModel.toString();

        if (currentState != null) {
            undoStates.addFirst(currentState);
        }
        currentState = state;
        redoStates.clear();
    }

    private void loadSate(IGameModel gameModel, String state) {
        GameLoader gameLoader = new GameLoader(gameModel);
        try {
            gameLoader.load(new ByteArrayInputStream(state.getBytes()));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void undo(IGameModel gameModel) {

        if (undoStates.isEmpty()) {
            return;
        }

        redoStates.addLast(currentState);
        currentState = undoStates.removeFirst();

        gameModel.reset();
        loadSate(gameModel, currentState);
    }

    public void redo(IGameModel gameModel) {
        if (redoStates.isEmpty()) {
            return;
        }

        undoStates.addFirst(currentState);
        currentState = redoStates.removeLast();

        gameModel.reset();
        loadSate(gameModel, currentState);
    }
}
