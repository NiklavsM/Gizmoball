package strath.cs308.gizmoball.view;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import strath.cs308.gizmoball.GizmoBall;
import strath.cs308.gizmoball.controller.GameBarEventHandler;
import strath.cs308.gizmoball.controller.GameLoader;
import strath.cs308.gizmoball.controller.InGameKeyEventHandler;
import strath.cs308.gizmoball.controller.PauseMenuEventHandler;
import strath.cs308.gizmoball.model.GameTimer;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.IGameTimer;
import strath.cs308.gizmoball.model.triggeringsystem.ITriggerable;

import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import java.util.ResourceBundle;

public class PlayView extends Stage implements IPlayView, Observer {

    private static final double WIDTH = 1000;
    private static final double HEIGHT = 800;
    private GizmoBall gizmoBall;
    private IGameModel gameModel;
    private BorderPane root;
    private ToolBar pauseMenu;
    private StackPane stackPane;
    private Canvas canvas;
    private InGameKeyEventHandler keyHandler;

    public PlayView(GizmoBall gizmoBall) {
        this.keyHandler = gizmoBall.getKeyHandler();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/plaview.fxml"));
            loader.setResources(ResourceBundle.getBundle("dictionary", gizmoBall.getLocale()));
            root = loader.load();

            this.gizmoBall = gizmoBall;
            this.gameModel = gizmoBall.getGameModel();
            pauseMenu = (ToolBar) root.lookup("#pauseMenu");
            stackPane = (StackPane) root.lookup("#stackPane");
            canvas = (Canvas) root.lookup("#canvas");

            drawBackground();
            drawGizmos();

            pauseMenu.toBack();
            gameModel.addObserver(this);

            Scene scene = new Scene(root, root.getWidth(), root.getHeight());
            scene.setOnKeyPressed(this.keyHandler);
            scene.setOnKeyReleased(this.keyHandler);

            super.setScene(scene);
            super.setWidth(WIDTH);
            super.setHeight(HEIGHT);
            super.setTitle("Gizmoball - Play");
            super.show();

            Platform.runLater(this::attachEventHandlers);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void attachEventHandlers() {
        Platform.runLater(() -> {
            IGameTimer gameTimer = new GameTimer(gameModel);
            EventHandler gameBarEventHandler = new GameBarEventHandler(gameModel, gameTimer, this);

            root.lookupAll("#gameMenu > Button")
                    .forEach(node -> ((Button) node).setOnAction(gameBarEventHandler));

            EventHandler pauseMenuEventHandler = new PauseMenuEventHandler(gizmoBall, gameTimer, this);
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
        if(o instanceof ITriggerable) {
            return;
        }
        Platform.runLater(() -> {
            drawBackground();
            drawGizmos();
            updateScore();
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
        score.setText("Score: " + gameModel.getScore());
    }

    public void changePlayIcon() {
        Button icon = (Button) root.lookup("#playButton");
        if (icon.getStyle().contains("stop")) {
            icon.setStyle("-fx-background-image: url('/icons/play.png')");
            icon.setTooltip(new Tooltip("Play"));
        }
        else {
            icon.setStyle("-fx-background-image: url('/icons/stop.png')");
            icon.setTooltip(new Tooltip("Stop"));
        }
    }

    public boolean getCloseConFormation() {
        Alert closeConfirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to quit the game?");
        Button exitButton = (Button) closeConfirmation.getDialogPane().lookupButton(
                ButtonType.OK
        );

        exitButton.setText("Exit");
        closeConfirmation.setHeaderText("Confirm Exit?");
        closeConfirmation.initModality(Modality.APPLICATION_MODAL);

        Optional<ButtonType> closeResponse = closeConfirmation.showAndWait();
        return ButtonType.OK.equals(closeResponse.get());
    }

    @Override
    public void switchToEditor() {
        gizmoBall.switchModes();
    }

    public void soundOn(boolean soundOn){
        Button soundButton = (Button) root.lookup("#soundButton");
        soundButton.getStyleClass().clear();
        if(soundOn) {
            soundButton.getStyleClass().add("sound-on-button");
        }else{
            soundButton.getStyleClass().add("sound-off-button");
        }

    }

    @Override
    public File getLoadFile() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("GIZMO (*.gizmo)", "*.gizmo");
        fileChooser.getExtensionFilters().add(filter);
        fileChooser.setTitle("Gizmoball loading file");
        fileChooser.setInitialFileName("hahahah");
        return fileChooser.showOpenDialog(null);
    }
}
