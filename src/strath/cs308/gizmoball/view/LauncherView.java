package strath.cs308.gizmoball.view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import strath.cs308.gizmoball.GizmoBall;
import strath.cs308.gizmoball.controller.LauncherEventHandler;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.triggeringsystem.ITriggerable;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class LauncherView extends Scene implements ILauncherView, Observer {

    private static final String TAG = "LauncherView";
    private IGameModel gameModel;
    private BorderPane root;

    public LauncherView(IGameModel gameModel) {
        super(new Pane());
        this.gameModel = gameModel;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/launcher.fxml"));
            loader.setResources(ResourceBundle.getBundle("dictionary", GizmoBall.locale));
            root = loader.load();

            setRoot(root);

            Platform.runLater(this::attachEventHandlers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void attachEventHandlers() {
        Platform.runLater(() -> {
            EventHandler<ActionEvent> launcherEventHandler = new LauncherEventHandler(gameModel, this);

            root.lookupAll("#launcherVbox > Button")
                    .forEach(node -> ((Button) node).setOnAction(launcherEventHandler));
        });
    }


    @Override
    public void update(Observable observable, Object o) {
        if (o instanceof ITriggerable) {
            return;
        }
        Platform.runLater(() -> {
//            drawBackground();
//            drawGizmos();
//            updateBallsInPlay();
//            updateScore();
        });
    }

    @Override
    public void switchToPlay() {
        gameModel.deleteObserver(this);
        GizmoBall.switchView(new PlayView(gameModel));
    }

    @Override
    public void switchToEditor() {
        gameModel.deleteObserver(this);
        GizmoBall.switchView(new EditorView(gameModel));
    }


}
