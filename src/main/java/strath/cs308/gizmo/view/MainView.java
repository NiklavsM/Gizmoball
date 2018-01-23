package strath.cs308.gizmo.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MainView
{
    private BorderPane root;

    public MainView()
    {
        try
        {
            this.root = FXMLLoader.load(this.getClass().getResource("/main.fxml"));

            GameView gameView = new GameView();
            this.root.setCenter(gameView.getParent());

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public Parent getParent()
    {
        return this.root;
    }
}
