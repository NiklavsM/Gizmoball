package strath.cs308.gizmoball.controller;

import strath.cs308.gizmoball.model.IGameModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class GameSaver {

    private IGameModel gameModel;
    private File fileToSave;

    public GameSaver(IGameModel gameModel, File fileToSave) {
        this.gameModel = gameModel;
        this.fileToSave = fileToSave;
    }

    public void save() throws IllegalAccessException, FileNotFoundException {
        PrintWriter printWriter = new PrintWriter(fileToSave);
        printWriter.print(gameModel.toString());
        printWriter.close();
    }
}
