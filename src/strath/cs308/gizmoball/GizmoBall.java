package strath.cs308.gizmoball;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import strath.cs308.gizmoball.controller.GameLoader;
import strath.cs308.gizmoball.controller.actions.ChangeToARandomColor;
import strath.cs308.gizmoball.model.GameModel;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.UndoRedo;
import strath.cs308.gizmoball.model.gizmo.IGizmo;
import strath.cs308.gizmoball.model.gizmo.Octagon;
import strath.cs308.gizmoball.model.gizmo.Square;
import strath.cs308.gizmoball.model.gizmo.Triangle;
import strath.cs308.gizmoball.model.triggeringsystem.ITrigger;
import strath.cs308.gizmoball.model.triggeringsystem.ITriggerable;
import strath.cs308.gizmoball.utils.Logger;
import strath.cs308.gizmoball.utils.Settings;
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


            loadSettings();

            gameModel.getGizmos().stream()
                    .filter(g -> (g instanceof Square) || (g instanceof Circle) || (g instanceof Octagon) || (g instanceof Triangle))
                    .map(ITriggerable.class::cast)
                    .forEach(triggerable -> {
                        ((ITrigger) triggerable).registerTriggerable(triggerable);
                        triggerable.setAction(new ChangeToARandomColor(gameModel, (IGizmo) triggerable, "#000000", "#ffffff", "#f12"));
                    });


            UndoRedo.INSTANCE.saveState(gameModel);
//            setIcon(); //FIX stopped working :( add

            //Doesn't work in xml
            primaryStage.setTitle("Gizmoball");
            primaryStage.setMinWidth(750);
            primaryStage.setMinHeight(600);

            //primaryStage.setScene(new EditorView(gameModel, keyHandler));
            primaryStage.setScene(new PlayView(gameModel));
            primaryStage.show();
            stage = primaryStage;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadSettings() throws IOException {

        Settings.reloadSettings();

        String language = Settings.getProperty("language");
        locale = new Locale(language);

        Logger.debug(TAG, "Language set to : " + language);
    }

    private static void setIcon() {
        Image image = new Image("images/icon.png");
        stage.getIcons().add(image);

    }

    public static void switchView(Scene view) {
        setIcon();
        stage.setScene(view);
    }

    @Override
    public void stop() {
        Settings.saveSettings();
        Platform.exit();
        System.exit(0);
    }

}