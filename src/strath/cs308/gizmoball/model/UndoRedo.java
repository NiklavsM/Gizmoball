package strath.cs308.gizmoball.model;

import strath.cs308.gizmoball.controller.GameLoader;
import strath.cs308.gizmoball.controller.GameSaver;
import strath.cs308.gizmoball.controller.InGameKeyEventHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.UUID;

public enum UndoRedo
{
    INSTANCE;

    private Deque<String> states;
    private String currentState;

    UndoRedo() {
        this.states = new LinkedList();
        currentState = null;
    }

    private File createFile(String fileName) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
        }

        return file;
    }

    public void saveState(IGameModel gameModel, InGameKeyEventHandler keyEventHandler) throws IOException, IllegalAccessException {

        String fileName = UUID.randomUUID().toString();
        currentState = fileName;
        states.addFirst(fileName);

        GameSaver saver = new GameSaver(gameModel, keyEventHandler, createFile(fileName));
        saver.save();
    }

    private void loadSate(IGameModel gameModel, InGameKeyEventHandler keyEventHandler, File file) throws FileNotFoundException, IllegalAccessException {
        GameLoader gameLoader = new GameLoader(gameModel, keyEventHandler);
        gameLoader.load(new FileInputStream(file));
    }

    public void undo(IGameModel gameModel, InGameKeyEventHandler keyEventHandler) throws FileNotFoundException, IllegalAccessException
    {
        Iterator<String> iterator = states.iterator();

        while (!iterator.next().equals(currentState));

        if (iterator.hasNext()) {
            String state = iterator.next();
            loadSate(gameModel, keyEventHandler, new File(state));
            currentState = state;
        }
    }

    public void redo(IGameModel gameModel, InGameKeyEventHandler keyEventHandler) {

    }
}
