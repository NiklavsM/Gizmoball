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
import strath.cs308.gizmoball.utils.Logger;
import strath.cs308.gizmoball.view.EditorView;

import java.io.IOException;
import java.util.Locale;

public class GizmoBall extends Application {

    private static final String TAG = "GizmoBall";
    public static Locale locale = new Locale("en");
    private static Stage stage;
    private static Scene view;
    private IGameModel gameModel;
    private InGameKeyEventHandler keyHandler;
    private GameLoader gameLoader;

    public static void main(String[] args) {
        launch(args);
    }

    public static void switchView(Scene view) {
        GizmoBall.view = view;
        stage.setScene(view);
    }

    @Override

    public void start(Stage primaryStage) throws IOException {
        primaryStage.setX(100);

        gameModel = new GameModel();
        keyHandler = new InGameKeyEventHandler(gameModel);
        gameLoader = new GameLoader(gameModel, keyHandler);

        try {
            gameLoader.load(getClass().getResourceAsStream("/alternative.gizmo"));
        } catch (Exception e) {
            Logger.error(TAG, "Failed to load default model");
            e.printStackTrace();
        }


        UndoRedo.INSTANCE.saveState(gameModel, keyHandler);

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

        //Doesn't work in xml
        primaryStage.setMinWidth(500);
        primaryStage.setMinHeight(530);


        primaryStage.setScene(new EditorView(gameModel, keyHandler));
        primaryStage.show();
        stage = primaryStage;
    }

    @Override
    public void stop() {
        Platform.exit();
        System.exit(0);
    }

}