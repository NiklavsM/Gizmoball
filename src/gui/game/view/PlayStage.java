package gui.game.view;

import gui.Theme;
import gui.editor.view.BoardCanvasView;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.Model;
import model.VerticalLine;
import view.GizmoView;


public class PlayStage extends Stage {

    private static final String APPLICATION_NAME = "Gizmoball - Play";

    private GizmoView gizmoView;
    private static final double APP_HEIGHT = 500;
    private static final double APP_WIDTH = 500;
    private Label scoreLabel;
    private StackPane stackPane;

    public PlayStage(GizmoView gizmoView) {
        this.gizmoView = gizmoView;

        setup();
    }

    private void setup() {
        Model model = makeModel();

        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 500, 500);

        // Center
        Canvas canvas = new BoardCanvasView(500, 500, model);
//        GameBar gameBar = new GameBar(Pos.BOTTOM_LEFT, root);

        stackPane = new StackPane();
        stackPane.setMaxHeight(APP_HEIGHT);
        stackPane.setMaxWidth(APP_WIDTH);

        GameBar gameBar = new GameBar(Pos.BOTTOM_LEFT, this, model);

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


    private Model makeModel() {
        Model model = new Model();

        model.setBallSpeed(200, 200);

        // Vertical line at (100,100), width 300
        model.addLine(new VerticalLine(100, 100, 300));
        model.addLine(new VerticalLine(100, 200, 300));
        model.addLine(new VerticalLine(100, 300, 300));
        model.addLine(new VerticalLine(100, 400, 300));

        return model;
    }

    public void showPauseMenu() {
        stackPane.getChildren().forEach(e -> e.setEffect(new GaussianBlur(10))); //blur it a little
        PauseMenu menu = new PauseMenu(this);
        stackPane.getChildren().add(menu);
    }

    public void openEditor() {
        gizmoView.switchModes();
    }
}
