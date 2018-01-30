package strath.cs308.gizmo.view;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import strath.cs308.gizmo.controller.PlayController;
import strath.cs308.gizmo.model.interfaces.IPhysicsWorld;
import strath.cs308.gizmo.view.interfaces.IPlayView;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class PlayView implements IPlayView, Observer
{
    private Pane root;
    private EventHandler controller;

    public PlayView(StackPane root, IPhysicsWorld world)
    {
        try
        {
            this.root = FXMLLoader.load(this.getClass().getResource("/play.fxml"));

            this.controller = new PlayController(this, world);
            world.addObserver(this);

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void update(Observable observable, Object o)
    {

    }
}
