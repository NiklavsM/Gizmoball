package strath.cs308.gizmoball.view;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import strath.cs308.gizmoball.controller.GameBarEventHandler;
import strath.cs308.gizmoball.model.IGameModel;

import java.io.IOException;

public class PlayView implements IPlayView{

    private IGameModel gameModel;
    private BorderPane root;
    private ToolBar pauseMenu;
    private StackPane stackPane;

    public PlayView(Stage stage, IGameModel gameModel) {

        try {

            root = FXMLLoader.load(getClass().getResource("/view/plaview.fxml"));
            this.gameModel = gameModel;
            pauseMenu = (ToolBar) root.lookup("#pauseMenu");
            stackPane = (StackPane) root.lookup("#stackPane");

            pauseMenu.toBack();

            Scene scene = new Scene(root, root.getWidth(), root.getHeight());

            stage.setScene(scene);
            stage.setWidth(root.getWidth());
            stage.setHeight(root.getHeight());
            stage.setTitle("Gizmoball - Play");
            stage.setScene(scene);
            stage.show();

            Platform.runLater(this::attachEventHandlers);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void attachEventHandlers() {
        root.lookupAll("#gameMenu > Button")
                .forEach(node -> ((Button) node).setOnAction(new GameBarEventHandler(gameModel, this)));
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
        pauseMenu.toFront();
        stackPane.getChildren()
                .stream()
                .filter(node -> !node.equals(pauseMenu))
                .forEach(node -> node.setEffect(new GaussianBlur(10)));
    }
}
