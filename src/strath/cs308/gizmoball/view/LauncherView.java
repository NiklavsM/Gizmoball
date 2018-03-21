package strath.cs308.gizmoball.view;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import strath.cs308.gizmoball.GizmoBall;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.UndoRedo;
import strath.cs308.gizmoball.model.triggeringsystem.ITriggerable;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class LauncherView extends Scene implements ILauncherView, Observer {

    private static final String TAG = "LauncherView";
    private IGameModel gameModel;
    private BorderPane root;
    private ToolBar pauseMenu;
    private StackPane stackPane;
    private Canvas canvas;

    public LauncherView(IGameModel gameModel) {
        super(new Pane());
        this.gameModel = gameModel;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/launcher.fxml"));
            loader.setResources(ResourceBundle.getBundle("dictionary", GizmoBall.locale));
            root = loader.load();

            pauseMenu = (ToolBar) root.lookup("#pauseMenu");
            stackPane = (StackPane) root.lookup("#stackPane");
            canvas = (Canvas) root.lookup("#canvas");

//            pauseMenu.toBack();
//            gameModel.addObserver(this);

//            EventHandler keyHandler = new InGameKeyEventHandler(gameModel);
//            setOnKeyPressed(keyHandler);
//            setOnKeyReleased(keyHandler);
            setRoot(root);

            Platform.runLater(this::attachEventHandlers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void attachEventHandlers() {
        Platform.runLater(() -> {
//            IGameTimer gameTimer = new GameTimer(gameModel);
//            EventHandler gameBarEventHandler = new GameBarEventHandler(gameModel, gameTimer, this);
//
//            root.lookupAll("#gameMenu > Button")
//                    .forEach(node -> ((Button) node).setOnAction(gameBarEventHandler));
//
//            EventHandler pauseMenuEventHandler = new PauseMenuEventHandler(gameModel, gameTimer, this);
//            root.lookupAll("#pauseMenuItemHolder > Button")
//                    .forEach(node -> ((Button) node).setOnAction(pauseMenuEventHandler));

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
        UndoRedo.INSTANCE.saveState(gameModel);
        gameModel.deleteObserver(this);
        GizmoBall.switchView(new EditorView(gameModel));
    }

    @Override
    public void switchToEditor() {
        UndoRedo.INSTANCE.saveState(gameModel);
        gameModel.deleteObserver(this);
        GizmoBall.switchView(new PlayView(gameModel));
    }

    public void soundOn(boolean soundOn) {
        Button soundButton = (Button) root.lookup("#soundButton");
        soundButton.getStyleClass().clear();
        if (soundOn) {
            soundButton.getStyleClass().add("sound-on-button");
        } else {
            soundButton.getStyleClass().add("sound-off-button");
        }
    }

}
