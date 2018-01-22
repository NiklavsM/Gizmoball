package strath.cs308.gizmo.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class GameView
{
    private BorderPane root;

    public GameView()
    {
        try
        {
            this.root = FXMLLoader.load(this.getClass().getResource("/game.fxml"));
        }
        catch (IOException e)
        {
            System.err.println("file is not reachable!");
        }
    }

    public Parent getParent()
    {
        return this.root;
    }



}
