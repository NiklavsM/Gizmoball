package strath.cs308.gizmoball;

import javafx.application.Application;
import javafx.stage.Stage;
import strath.cs308.gizmoball.controller.GameLoader;
import strath.cs308.gizmoball.model.GameModel;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.view.PlayView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GizmoBall extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        IGameModel gameModel = new GameModel();

        GameLoader gameLoader = new GameLoader(gameModel, getClass().getResourceAsStream("/maps/default.gizmo"));

        try {
            gameLoader.load();
        } catch (IllegalAccessException e) {
            System.err.println("Failed to load default model");
            System.err.println("\n\n");
            e.printStackTrace();
        }

        PlayView playView = new PlayView(stage, gameModel);
    }
}