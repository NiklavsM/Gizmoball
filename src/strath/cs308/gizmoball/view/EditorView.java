package strath.cs308.gizmoball.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import strath.cs308.gizmoball.model.IGameModel;

import java.io.IOException;

public class EditorView
{
    public EditorView(Stage stage, IGameModel gameModel) {

        try
        {
            BorderPane root = FXMLLoader.load(getClass().getResource("/view/editorview.fxml"));

            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.setTitle("Gizmoball - Editor");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
