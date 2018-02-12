package gui;

import gui.menu.PauseMenu;
import gui.panel.BoardCanvasView;
import gui.toolbar.GameBar;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.GameModel;


public class PlayStage extends Stage {

    private static final String APPLICATION_NAME = "Gizmoball - Play";

    private GizmoView gizmoView;
    private GameModel gameModel;
    private static final double APP_HEIGHT = 500;
    private static final double APP_WIDTH = 500;
    private Label scoreLabel;
    private StackPane stackPane;
    private GameBar gameBar;

    public PlayStage(GizmoView gizmoView) {
        this.gizmoView = gizmoView;
        this.gameModel = gizmoView.getGameModel();
        setup();
    }

    private void setup() {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 500, 500);

        // Center
        Canvas canvas = new BoardCanvasView(500, 500, gizmoView.getGameModel());
//        GameBar gameBar = new GameBar(Pos.BOTTOM_LEFT, root);

        stackPane = new StackPane();
        stackPane.setMaxHeight(APP_HEIGHT);
        stackPane.setMaxWidth(APP_WIDTH);

        gameBar = new GameBar(Pos.BOTTOM_LEFT, this, gizmoView.getGameModel());

        // Score
        scoreLabel = new Label("Score: 1337");
        scoreLabel.setPadding(Theme.Padding.DEFAULT_PADDING);
        scoreLabel.setFont(Theme.Fonts.TITLE_FONT);

        StackPane.setAlignment(scoreLabel, Pos.TOP_RIGHT);
        StackPane.setAlignment(gameBar, Pos.BOTTOM_LEFT);

        stackPane.getChildren().add(canvas);
        stackPane.getChildren().add(scoreLabel);
        stackPane.getChildren().add(gameBar);

        root.setCenter(stackPane);
        scene.getStylesheets().add(Theme.STYLESHEET_PATH);

        super.setMinHeight(APP_HEIGHT);
        super.setMinWidth(APP_WIDTH);
        super.setScene(scene);
        super.setTitle(APPLICATION_NAME);
        super.show();
    }

    public void showPauseMenu() {
        stackPane.getChildren().forEach(e -> e.setEffect(new GaussianBlur(10))); //blur it a little
        PauseMenu menu = new PauseMenu(this);
        stackPane.getChildren().add(menu);
        gameBar.disabled(true);
    }

    public GameModel getGameModel() {
        return gameModel;
    }

    public void openEditor() {
        gizmoView.switchModes();
    }

    public GameBar getGameBar() {
        return gameBar;
    }
}
