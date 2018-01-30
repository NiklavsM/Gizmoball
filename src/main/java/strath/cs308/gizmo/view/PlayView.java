package strath.cs308.gizmo.view;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import strath.cs308.gizmo.controller.PlayController;
import strath.cs308.gizmo.model.interfaces.IPhysicsWorld;
import strath.cs308.gizmo.view.interfaces.IPlayView;

import java.io.IOException;

public class PlayView implements IPlayView
{
    private Pane root;
    private EventHandler controller;

    public PlayView(StackPane root, IPhysicsWorld world)
    {
        try
        {
            this.root = FXMLLoader.load(this.getClass().getResource("/play.fxml"));

            this.controller = new PlayController(this, world);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}
