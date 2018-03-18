package strath.cs308.gizmoball;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import strath.cs308.gizmoball.controller.GameLoader;
import strath.cs308.gizmoball.controller.InGameKeyEventHandler;
import strath.cs308.gizmoball.model.GameModel;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.gizmo.Flipper;
import strath.cs308.gizmoball.model.gizmo.Triangle;
import strath.cs308.gizmoball.utils.Logger;
import strath.cs308.gizmoball.view.EditorView;
import strath.cs308.gizmoball.view.IEditorView;
import strath.cs308.gizmoball.view.PlayView;

import java.util.Locale;

public class GizmoBall extends Application {

    private static final String TAG = "GizmoBall";
    private IGameModel gameModel;
    private InGameKeyEventHandler keyHandler;
    private GameLoader gameLoader;
    public static Locale locale = new Locale("en");
    private static Stage stage;
    private static Scene view;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setX(100);

        gameModel = new GameModel();
        keyHandler = new InGameKeyEventHandler(gameModel);
        gameLoader = new GameLoader(gameModel, keyHandler);

        try {
            gameLoader.load(getClass().getResourceAsStream("/alternative.gizmo"));
        } catch (Exception e) {
            Logger.error(TAG,"Failed to load default model");
            e.printStackTrace();
        }

        //
        // THIS IS JUST TESTING HERE
        //
//        Triangle t = (Triangle) gameModel.getGizmoById("T");
//        t.setAction(args -> {
//            t.rotate();
//        });
//        t.registerTriggarable(t);  Flipper f = (Flipper) gameModel.getGizmoById("RF112");
//        t.registerTriggarable(f);
//        keyHandler.onKeyEventTrigger("key 74.0 down", t);
        //
        //
        //
        //
        //

        primaryStage.setScene(new PlayView(gameModel, keyHandler));
        primaryStage.show();
        stage = primaryStage;
    }


    public static void switchView(Scene view) {
        GizmoBall.view = view;
        stage.setScene(view);
    }

    @Override
    public void stop() {
        Platform.exit();
        System.exit(0);
    }

}