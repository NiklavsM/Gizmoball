package strath.cs308.gizmoball.controller;

import strath.cs308.gizmoball.GizmoBall;
import strath.cs308.gizmoball.model.IGameModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class GameSaver {

    private IGameModel gameModel;
    private GizmoBall gizmoBall;
    private File fileToSave;

    public GameSaver(GizmoBall gizmoBall, File fileToSave) {
        this.gizmoBall = gizmoBall;
        this.gameModel = gizmoBall.getGameModel();
        this.fileToSave = fileToSave;
    }

    public void save() throws IllegalAccessException, FileNotFoundException {
        PrintWriter printWriter = new PrintWriter(fileToSave);
        printWriter.print(gameModel.toString());
        printWriter.println("\n\n# key bindings");
        printWriter.println(gizmoBall.getKeyHandler());
        printWriter.close();
    }
}
