package strath.cs308.gizmoball.view;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import strath.cs308.gizmoball.controller.TopToolbarEventHandler;
import strath.cs308.gizmoball.model.IGameModel;

import java.io.IOException;

public class EditorView implements IEditorView
{
    private BorderPane root;
    private IGameModel gameModel;

    public EditorView(Stage stage, IGameModel gameModel) {

        try
        {
            root = FXMLLoader.load(getClass().getResource("/view/editorview.fxml"));
            this.gameModel = gameModel;

            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.setTitle("Gizmoball - Editor");
            stage.show();

            Platform.runLater(this::attachHandlers);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void attachHandlers() {
        Platform.runLater(() -> {
        root.lookupAll(".top-toolbar-button")
                .forEach(node -> {
                    ((Button) node).setOnMouseClicked(new TopToolbarEventHandler(gameModel, this));
                });

        });
    }

    @Override
    public void switchToPlay() {
        PlayView playView = new PlayView((Stage) root.getScene().getWindow(), gameModel);
    }
}
