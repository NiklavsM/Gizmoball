package strath.cs308.gizmo.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import strath.cs308.gizmo.controller.GameController;
import strath.cs308.gizmo.model.interfaces.IPhysicsWorld;
import strath.cs308.gizmo.view.helper.ShapeFactory;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class GameView implements Observer
{
    private Pane root;

    public GameView(IPhysicsWorld world)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/game.fxml"));
            this.root = loader.load();
            GameController controller = loader.getController();
            controller.setWorld(world);
            controller.setView(this);

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
