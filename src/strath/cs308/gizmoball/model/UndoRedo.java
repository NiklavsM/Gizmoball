package strath.cs308.gizmoball.model;

import strath.cs308.gizmoball.controller.GameLoader;
import strath.cs308.gizmoball.controller.InGameKeyEventHandler;

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


    public void saveState(IGameModel gameModel, InGameKeyEventHandler keyEventHandler) {

        StringBuilder builder = new StringBuilder();
        builder.append(gameModel.toString())
                .append(keyEventHandler.toString());

        String state = builder.toString();

        if (currentState != null) {
            undoStates.addFirst(currentState);
        }
        currentState = state;
        redoStates.clear();
    }

    private void loadSate(IGameModel gameModel, InGameKeyEventHandler keyEventHandler, String state) {
        GameLoader gameLoader = new GameLoader(gameModel, keyEventHandler);
        try {
            gameLoader.load(new ByteArrayInputStream(state.getBytes()));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void undo(IGameModel gameModel, InGameKeyEventHandler keyEventHandler) {

        if (undoStates.isEmpty()) {
            return;
        }

        redoStates.addLast(currentState);
        currentState = undoStates.removeFirst();

        gameModel.reset();
        keyEventHandler.removeAllHandlers();
        loadSate(gameModel, keyEventHandler, currentState);
    }

    public void redo(IGameModel gameModel, InGameKeyEventHandler keyEventHandler) {
        if (redoStates.isEmpty()) {
            return;
        }

        undoStates.addFirst(currentState);
        currentState = redoStates.removeLast();

        gameModel.reset();
        keyEventHandler.removeAllHandlers();
        loadSate(gameModel, keyEventHandler, currentState);
    }
}
