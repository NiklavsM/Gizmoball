package strath.cs308.gizmoball;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import strath.cs308.gizmoball.controller.file.GameLoader;
import strath.cs308.gizmoball.model.GameModel;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.UndoRedo;
import strath.cs308.gizmoball.model.triggeringsystem.ITriggerable;
import strath.cs308.gizmoball.model.triggeringsystem.actions.GoToJailAction;
import strath.cs308.gizmoball.utils.Logger;
import strath.cs308.gizmoball.utils.Settings;
import strath.cs308.gizmoball.view.LauncherView;


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
                gameLoader.load(getClass().getResourceAsStream("/default.gizmo"));
            } catch (Exception e) {
                Logger.error(TAG, "Failed to load default model");
                e.printStackTrace();
            }

//            gameModel.getGizmos().stream()
//                    .filter(g -> (g instanceof Square) || (g instanceof Circle) || (g instanceof Octagon) || (g instanceof Triangle))
//                    .map(ITriggerable.class::cast)
//                    .forEach(triggerable -> {
//                        ((ITrigger) triggerable).registerTriggerable(triggerable);
//                        triggerable.setAction(new ChangeToARandomColor(gameModel, (IGizmo) triggerable, "#000000", "#ffffff", "#f12"));
//                    });
//
//

            ((ITriggerable) gameModel.getGizmoById("853191b3-6b1b-41f7-a047-739e41c82243")).setAction(new GoToJailAction(gameModel));

            loadSettings();

            UndoRedo.INSTANCE.saveState(gameModel);

            //Doesn't work in xml
            primaryStage.setTitle("Gizmoball");
            primaryStage.setMinWidth(750);
            primaryStage.setMinHeight(600);

            //primaryStage.setScene(new EditorView(gameModel, keyHandler));
            primaryStage.setScene(new LauncherView(gameModel));
            primaryStage.show();
            stage = primaryStage;

            setIcon();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setIcon() {
        Image image = new Image("images/icon.png");
        stage.getIcons().add(image);
    }

    public static void switchView(Scene view) {
        stage.setScene(view);
    }

    private void loadSettings() throws IOException {

        Settings.reloadSettings();

        String language = Settings.getProperty("language");
        locale = new Locale(language);

        Logger.debug(TAG, "Language set to : " + language);
    }

    @Override
    public void stop() {
        Settings.saveSettings();
        Platform.exit();
        System.exit(0);
    }
}