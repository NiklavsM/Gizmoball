package controller.play;

import controller.GameLoader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.IGameModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class LoadProgressHandler implements EventHandler<ActionEvent> {

    private final IGameModel gameModel;
    private final Callback backCllbck;
    private final GetFileCallback getFileCllbck;

    public LoadProgressHandler(IGameModel gameModel, Callback backCllbck, GetFileCallback getFileCllbck) {
        this.gameModel = gameModel;
        this.backCllbck = backCllbck;
        this.getFileCllbck = getFileCllbck;
    }

    @Override
    public void handle(ActionEvent event) {
        File file = getFileCllbck.call();

        if(file == null) {
            System.out.println("Loading file dialog cancelled");
            return;
        }

        try {
            gameModel.reset();
            GameLoader gl = new GameLoader(gameModel, new FileInputStream(file));
            try {
                gl.load();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            finally {
                backCllbck.call();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}