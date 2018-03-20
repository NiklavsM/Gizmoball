package strath.cs308.gizmoball;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import strath.cs308.gizmoball.controller.GameLoader;
import strath.cs308.gizmoball.controller.actions.*;
import strath.cs308.gizmoball.model.GameModel;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.UndoRedo;
import strath.cs308.gizmoball.model.gizmo.Triangle;
import strath.cs308.gizmoball.utils.Logger;
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
            GameLoader gameLoader = new GameLoader(gameModel);

            try {
                gameLoader.load(getClass().getResourceAsStream("/alternative.gizmo"));
            } catch (Exception e) {
                Logger.error(TAG, "Failed to load default model");
                e.printStackTrace();
            }

            Triangle t = (Triangle) gameModel.getGizmoById("T");
//            t.setAction(new RotateAction(gameModel, t));
//            t.setAction(new ChangeToARandomColor(gameModel, t, "#ffffff", "#000000", "#f32", t.getColor(), "#755785", "#f1f3f3"));
//            t.setAction(new RemoveGizmoAction(gameModel, gameModel.getGizmoById("RF112")));
            t.setAction(new DestroyerGizmoAction(gameModel));
            System.out.println(t.getCurrentAction());

            UndoRedo.INSTANCE.saveState(gameModel);

            //Doesn't work in xml
            primaryStage.setTitle("Gizmoball");
            primaryStage.setMinWidth(750);
            primaryStage.setMinHeight(600);

            //primaryStage.setScene(new EditorView(gameModel, keyHandler));
            primaryStage.setScene(new PlayView(gameModel));
            primaryStage.show();
            stage = primaryStage;
            setIcon();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setIcon() {
        Image image = new Image("/images/icon.png");
        stage.getIcons().add(image);
//        Application.getApplication().setDockIconImage(new ImageIcon("Football.png").getImage());

    }

    public static void switchView(Scene view) {
        setIcon();
        stage.setScene(view);
    }

    @Override
    public void stop() {
        Platform.exit();
        System.exit(0);
    }

}