package strath.cs308.gizmoball.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class PlayView {

    public PlayView(Stage stage) {

        try {

            BorderPane root = FXMLLoader.load(getClass().getResource("/view/plaview.fxml"));

            Scene scene = new Scene(root, root.getWidth(), root.getHeight());

            stage.setScene(scene);
            stage.setWidth(root.getWidth());
            stage.setHeight(root.getHeight());
            stage.setTitle("Gizmoball - Play");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
