package strath.cs308.gizmoball;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import strath.cs308.gizmoball.controller.GameLoader;
import strath.cs308.gizmoball.controller.InGameKeyEventHandler;
import strath.cs308.gizmoball.model.GameModel;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.UndoRedo;
import strath.cs308.gizmoball.model.gizmo.Triangle;
import strath.cs308.gizmoball.model.triggeringsystem.ITrigger;
import strath.cs308.gizmoball.utils.Logger;
import strath.cs308.gizmoball.view.EditorView;
import strath.cs308.gizmoball.view.PlayView;

import java.io.IOException;
import java.util.Locale;

public class GizmoBall extends Application {

    private static final String TAG = "GizmoBall";
    public static Locale locale = new Locale("en");
    private static Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            primaryStage.setX(100);

            IGameModel gameModel = new GameModel();
            InGameKeyEventHandler keyHandler = new InGameKeyEventHandler(gameModel);
            GameLoader gameLoader = new GameLoader(gameModel, keyHandler);

            try {
                gameLoader.load(getClass().getResourceAsStream("/alternative.gizmo"));
            } catch (Exception e) {
                Logger.error(TAG, "Failed to load default model");
                e.printStackTrace();
            }


            UndoRedo.INSTANCE.saveState(gameModel, keyHandler);

            //Doesn't work in xml
            primaryStage.setMinWidth(500);
            primaryStage.setMinHeight(530);

            //primaryStage.setScene(new EditorView(gameModel, keyHandler));
            primaryStage.setScene(new PlayView(gameModel, keyHandler));
            primaryStage.show();
            stage = primaryStage;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void switchView(Scene view) {
        stage.setScene(view);
    }

    @Override
    public void stop() {
        Platform.exit();
        System.exit(0);
    }

}