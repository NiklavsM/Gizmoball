package strath.cs308.gizmoball.controller;

import strath.cs308.gizmoball.model.IGameModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public enum GameSaver {
    INSTANCE;

    private File currentFile = null;

    public void save(IGameModel gameModel) throws IllegalAccessException, FileNotFoundException {
        PrintWriter printWriter = new PrintWriter(currentFile);
        printWriter.print(gameModel.toString());
        printWriter.close();
    }

    public File getCurrentFile() {
        return currentFile;
    }

    public void setCurrentFile(File currentFile) {
        this.currentFile = currentFile;
    }

    public boolean hasCurrentFile() {
        return currentFile != null;
    }
}
