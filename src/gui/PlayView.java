package gui;

import controller.*;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.GameTimer;
import model.IGameModel;
import model.IGameTimer;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class PlayView implements IPlayView, Observer {

    private BorderPane root;
    private IGameModel gameModel;
    private Canvas canvas;
    private ToolBar pauseMenu;
    private StackPane stackPane;

    public PlayView(Stage stage, IGameModel gameModel) {


        try {
            root = FXMLLoader.load(getClass().getResource("/playview.fxml"));
            canvas = (Canvas) root.lookup("#playCanvas");
            pauseMenu = (ToolBar) root.lookup("#pauseMenu");
            stackPane = (StackPane) root.lookup("#playStack");
            this.gameModel = gameModel;
            this.gameModel.addObserver(this);


            clearGameArea();
            redraw();

            Scene scene = new Scene(root, 500, 500);
            scene.setOnKeyPressed(new KeyboardTriggerEventHandler(gameModel));

            stage.setTitle("Gizmoball - Play");
            stage.setScene(scene);

            Platform.runLater(this::addEventHandlers);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void addEventHandlers() {
        IGameTimer gameTimer = new GameTimer(gameModel);
        ((Button) root.lookup("#playButton")).setOnAction(new PlayButtonEventHandler(gameTimer));
        ((Button) root.lookup("#stopButton")).setOnAction(new StopButtonEventHandler(gameTimer));
        ((Button) root.lookup("#tickButton")).setOnAction(new TickButtonEventHandler(gameModel));
        ((Button) root.lookup("#menuButton")).setOnAction(new MenuButtonEventHandler(this, gameTimer));

        ((Button) root.lookup("#menuBackButton")).setOnAction(new BackToGameHandler(this, gameTimer));

    }

    @Override
    public void update(Observable observable, Object o) {
        clearGameArea();
        redraw();
    }

    private void redraw() {
        GizmoDrawer shapeDrawer = new GizmoDrawer(canvas);
        gameModel.getGizmos().forEach(shapeDrawer::drawGizmo);
    }

    private void clearGameArea() {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(Color.rgb(84, 110, 122));
        graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    @Override
    public void showPauseMenu() {

        this.pauseMenu.toFront();
        this.stackPane.getChildren()
                .stream()
                .filter(node -> !node.equals(this.pauseMenu))
                .forEach(node -> node.setEffect(new GaussianBlur(10)));

    }

    public void hidePauseMenu () {
        this.pauseMenu.toBack();
        this.stackPane.getChildren()
                .forEach(node -> node.setEffect(new GaussianBlur(0)));
    }
}
