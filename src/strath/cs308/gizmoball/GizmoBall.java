package strath.cs308.gizmoball;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import strath.cs308.gizmoball.controller.GameLoader;
import strath.cs308.gizmoball.controller.IngameKeyEventHandler;
import strath.cs308.gizmoball.model.GameModel;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.view.PlayView;

public class GizmoBall extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setX(100);

        IGameModel gameModel = new GameModel();
        EventHandler keyHandler = new IngameKeyEventHandler(gameModel);
        GameLoader gameLoader = new GameLoader(gameModel, keyHandler, getClass().getResourceAsStream("/alternative.gizmo"));

        try {
            gameLoader.load();
        } catch (IllegalAccessException e) {
            System.err.println("Failed to load default model");
            System.err.println("\n\n");
            e.printStackTrace();
        }

        PlayView playView = new PlayView(stage, gameModel, keyHandler);
    }

    @Override
    public void stop() {
        Platform.exit();
        System.exit(0);
    }
}