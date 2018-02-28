package strath.cs308.gizmoball.view;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ToolBar;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import strath.cs308.gizmoball.controller.GameBarEventHandler;
import strath.cs308.gizmoball.controller.IngameKeyEventHandler;
import strath.cs308.gizmoball.controller.PauseMenuEventHandler;
import strath.cs308.gizmoball.model.GameTimer;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.IGameTimer;

import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

public class PlayView implements IPlayView, Observer {

    private IGameModel gameModel;
    private BorderPane root;
    private ToolBar pauseMenu;
    private StackPane stackPane;
    private Canvas canvas;

    public PlayView(Stage stage, IGameModel gameModel) {

        try {
            root = FXMLLoader.load(getClass().getResource("/view/plaview.fxml"));
            this.gameModel = gameModel;
            pauseMenu = (ToolBar) root.lookup("#pauseMenu");
            stackPane = (StackPane) root.lookup("#stackPane");
            canvas = (Canvas) root.lookup("#canvas");

            drawBackground();
            drawGizmos();

            pauseMenu.toBack();
            gameModel.addObserver(this);

            EventHandler ingameKeyHandler = new IngameKeyEventHandler(gameModel);

            Scene scene = new Scene(root, root.getWidth(), root.getHeight());
            scene.setOnKeyPressed(ingameKeyHandler);
            scene.setOnKeyReleased(ingameKeyHandler);

            stage.setScene(scene);
            stage.setWidth(root.getWidth());
            stage.setHeight(root.getHeight());
            stage.setTitle("Gizmoball - Play");
            stage.show();

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

            EventHandler pauseMenuEventHandler = new PauseMenuEventHandler(gameModel, gameTimer, this);
            root.lookupAll("#pauseMenuItemHolder > Button")
                    .forEach(node -> ((Button) node).setOnAction(pauseMenuEventHandler));


        });
    }

    @Override
    public void showPauseMenu() {

        pauseMenu.toFront();
        stackPane.getChildren()
                .stream()
                .filter(node -> !node.equals(pauseMenu))
                .forEach(node -> node.setEffect(new GaussianBlur(10)));

    }

    public void hidePauseMenu() {
        pauseMenu.toBack();
        stackPane.getChildren()
                .stream()
                .filter(node -> !node.equals(pauseMenu))
                .forEach(node -> node.setEffect(new GaussianBlur(0)));
    }

    @Override
    public void update(Observable observable, Object o) {
        Platform.runLater(() -> {
            drawBackground();
            drawGizmos();
        });
    }

    private void drawBackground() {
        GraphicsContext graphics = canvas.getGraphicsContext2D();
        graphics.setFill(GizmoDrawer.DEEP_BLUE);
        graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

    }

    private void drawGizmos() {
        GizmoDrawer drawer = new GizmoDrawer(canvas);
        gameModel.getGizmos().forEach(drawer::drawGizmo);
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
        EditorView editorView = new EditorView((Stage) root.getScene().getWindow(), gameModel);
    }

    @Override
    public File getLoadFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Gizmoball loading file");
        fileChooser.setInitialFileName("hahahah");
        return fileChooser.showOpenDialog(null);
    }
}
