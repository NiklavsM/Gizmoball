package strath.cs308.gizmo.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class GameView implements Observer
{
    private Pane root;

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


    @Override
    public void update(Observable observable, Object o)
    {

    }
}
