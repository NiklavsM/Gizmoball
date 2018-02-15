package controller.play;

import controller.GameLoader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import model.IGameModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class LoadProgressHandler implements EventHandler<ActionEvent> {

    private final IGameModel gameModel;
    private Callback callback;

    public LoadProgressHandler(IGameModel gameModel, Callback callback) {
        this.gameModel = gameModel;
        this.callback = callback;
    }

    @Override
    public void handle(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Gizmoball loading file");
        fileChooser.setInitialFileName("hahahah");
        File file = fileChooser.showOpenDialog(null);
        gameModel.reset();
        try {
            GameLoader gl = new GameLoader(gameModel, new FileInputStream(file));
            try {
                gl.load();
                callback.call();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}