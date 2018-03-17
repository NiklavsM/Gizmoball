package strath.cs308.gizmoball;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import strath.cs308.gizmoball.controller.GameLoader;
import strath.cs308.gizmoball.controller.InGameKeyEventHandler;
import strath.cs308.gizmoball.model.GameModel;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.gizmo.Flipper;
import strath.cs308.gizmoball.model.gizmo.Triangle;
import strath.cs308.gizmoball.view.EditorView;
import strath.cs308.gizmoball.view.IEditorView;
import strath.cs308.gizmoball.view.PlayView;

import java.util.Locale;

public class GizmoBall extends Application {

    private IGameModel gameModel;
    private Stage currentStage;
    private InGameKeyEventHandler keyHandler;
    private GameLoader gameLoader;
    private Locale locale = new Locale("en");

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
        } catch (IllegalAccessException e) {
            System.err.println("Failed to load default model");
            System.err.println("\n\n");
            e.printStackTrace();
        }

        //
        //
        //
        //
        // THIS IS JUST TESTING HERE
        //
        Triangle t = (Triangle) gameModel.getGizmoById("T");
        t.setAction(args -> {
            t.rotate();
        });
        t.registerTriggarable(t);
        //
        //
        //
        //
        //


        Flipper f = (Flipper) gameModel.getGizmoById("RF112");
        t.registerTriggarable(f);
        keyHandler.onKeyEventTrigger("key 74.0 down", t);

        currentStage = primaryStage;

        currentStage = new PlayView(this);
        currentStage.show();
    }


    public void switchModes() {
        currentStage.close();
        currentStage = currentStage instanceof PlayView
                ? new EditorView(this)
                : new PlayView(this);
        currentStage.show();
    }


    public IGameModel getGameModel() {
        return gameModel;
    }

    @Override
    public void stop() {
        Platform.exit();
        System.exit(0);
    }

    public GameLoader getGameLoader() {
        return gameLoader;
    }

    public InGameKeyEventHandler getKeyHandler() {
        return keyHandler;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

}