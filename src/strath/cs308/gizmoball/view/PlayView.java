package strath.cs308.gizmoball.view;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import strath.cs308.gizmoball.GizmoBall;
import strath.cs308.gizmoball.controller.GameBarEventHandler;
import strath.cs308.gizmoball.controller.InGameKeyEventHandler;
import strath.cs308.gizmoball.controller.PauseMenuEventHandler;
import strath.cs308.gizmoball.model.GameTimer;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.IGameTimer;
import strath.cs308.gizmoball.model.UndoRedo;
import strath.cs308.gizmoball.model.triggeringsystem.ITriggerable;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import java.util.ResourceBundle;

public class PlayView extends Scene implements IPlayView, Observer {

    private IGameModel gameModel;
    private BorderPane root;
    private ToolBar pauseMenu;
    private StackPane stackPane;
    private Canvas canvas;

    public PlayView(IGameModel gameModel) {
        super(new Pane());
        this.gameModel = gameModel;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/plaview.fxml"));
            loader.setResources(ResourceBundle.getBundle("dictionary", GizmoBall.locale));
            root = loader.load();

            pauseMenu = (ToolBar) root.lookup("#pauseMenu");
            stackPane = (StackPane) root.lookup("#stackPane");
            canvas = (Canvas) root.lookup("#canvas");

            drawBackground();
            drawGizmos();

            pauseMenu.toBack();
            gameModel.addObserver(this);

            EventHandler keyHandler = new InGameKeyEventHandler(gameModel);
            setOnKeyPressed(keyHandler);
            setOnKeyReleased(keyHandler);
            setRoot(root);

            Platform.runLater(this::attachEventHandlers);
        } catch (IOException e) {
            e.printStackTrace();
        }

        updateBallsInPlay();
    }

    private void attachEventHandlers() {
        Platform.runLater(() -> {
            IGameTimer gameTimer = new GameTimer(gameModel);
            EventHandler gameBarEventHandler = new GameBarEventHandler(gameModel, gameTimer, this);

            root.lookupAll("#gameMenu > Button")
                    .forEach(node -> ((Button) node).setOnAction(gameBarEventHandler));

            EventHandler pauseMenuEventHandler = new PauseMenuEventHandler(gameModel, gameTimer, this);
            root.lookupAll("#pauseMenuItemHolder > Button")
                    .forEach(node -> ((Button) node).setOnAction(pauseMenuEventHandler));

        });
    }

    @Override
    public void showPauseMenu() {
        root.lookup("#gameMenu").setDisable(true);
        pauseMenu.toFront();
        stackPane.getChildren()
                .stream()
                .filter(node -> !node.equals(pauseMenu))
                .forEach(node -> node.setEffect(new GaussianBlur(10)));

    }

    public void hidePauseMenu() {
        root.lookup("#gameMenu").setDisable(false);
        pauseMenu.toBack();
        stackPane.getChildren()
                .stream()
                .filter(node -> !node.equals(pauseMenu))
                .forEach(node -> node.setEffect(new GaussianBlur(0)));
    }

    @Override
    public void update(Observable observable, Object o) {
        if (o instanceof ITriggerable) {
            return;
        }
        Platform.runLater(() -> {
            drawBackground();
            drawGizmos();
            updateScore();
            updateBallsInPlay();
            updateTotals();
        });
    }

    private void drawBackground() {
        GraphicsContext graphics = canvas.getGraphicsContext2D();
        graphics.setFill(GizmoDrawer.DEEP_BLUE);
        graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private void drawGizmos() {
        GizmoDrawer gizmoDrawer = new GizmoDrawer(canvas);
        gameModel.getGizmos().forEach(gizmo -> gizmoDrawer.drawGizmo(gizmo, false));
        gameModel.getGizmoBalls().forEach(ball -> gizmoDrawer.drawGizmo(ball, false));
    }

    private void updateScore() {
        Label score = (Label) root.lookup("#score");
        DecimalFormat formatter = new DecimalFormat("#,###");
        score.setText(formatter.format(gameModel.getScore()));
    }

    private void updateBallsInPlay() {
        int[] balls = gameModel.getBallsInPlay();
        Label ballsInPlay = (Label) root.lookup("#ballsInPlay");
        ballsInPlay.setText(String.valueOf(balls[0]));

        Label ballsAbsorbed = (Label) root.lookup("#ballsAbsorbed");
        ballsAbsorbed.setText(String.valueOf(balls[1]));
    }

    private void updateTotals() {
        int[] total = gameModel.getTotalStatistics();
        Label totalCollisions = (Label) root.lookup("#totalCollisions");
        totalCollisions.setText(String.valueOf(total[0]));

        Label totalBallsAbsorbed = (Label) root.lookup("#totalAbsorbed");
        totalBallsAbsorbed.setText(String.valueOf(total[1]));
    }

    public void changePlayIcon(boolean isRunning) {
        Button icon = (Button) root.lookup("#playButton");
        if (!isRunning) {
            icon.setStyle("-fx-background-image: url('/icons/play.png')");
            icon.setTooltip(new Tooltip("Play"));
        } else {
            icon.setStyle("-fx-background-image: url('/icons/stop.png')");
            icon.setTooltip(new Tooltip("Stop"));
        }
    }

    public boolean getCloseConfirmation() {
        ResourceBundle dictionary = ResourceBundle.getBundle("dictionary", GizmoBall.locale);
        Alert closeConfirmation = new Alert(Alert.AlertType.CONFIRMATION, dictionary.getString("INGAME_MENU_EXIT_QUESTION"));
        Button exitButton = (Button) closeConfirmation.getDialogPane().lookupButton(
                ButtonType.OK
        );

        exitButton.setText(dictionary.getString("INGAME_MENU_EXIT_BUTTON"));
        closeConfirmation.setHeaderText(dictionary.getString("INGAME_MENU_EXIT_TITLE"));
        closeConfirmation.initModality(Modality.APPLICATION_MODAL);

        Optional<ButtonType> closeResponse = closeConfirmation.showAndWait();
        return ButtonType.OK.equals(closeResponse.get());
    }

    @Override
    public void switchToEditor() {
        UndoRedo.INSTANCE.saveState(gameModel);
        gameModel.deleteObserver(this);
        GizmoBall.switchView(new EditorView(gameModel));
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
