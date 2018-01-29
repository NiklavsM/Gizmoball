package strath.cs308.gizmo.view;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import strath.cs308.gizmo.controller.GameController;
import strath.cs308.gizmo.controller.interfaces.Controller;
import strath.cs308.gizmo.model.interfaces.IPhysicsWorld;
import strath.cs308.gizmo.view.helper.ShapeFactory;
import strath.cs308.gizmo.view.interfaces.IGameView;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class GameView implements IGameView, Observer
{
    private Pane root;
    private EventHandler eventHandler;

    public GameView(BorderPane parent, IPhysicsWorld world)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/game.fxml"));
            this.root = loader.load();

            parent.setCenter(this.root);

            this.eventHandler = new GameController(world, this);

            world.addObserver(this);

            ShapeFactory factory = new ShapeFactory();
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
