package strath.cs308.gizmo.view;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import strath.cs308.gizmo.controller.GameController;
import strath.cs308.gizmo.model.interfaces.IPhysicsWorld;
import strath.cs308.gizmo.view.helper.ShapeFactory;
import strath.cs308.gizmo.view.interfaces.IGameView;
import strath.cs308.gizmo.view.interfaces.IIngameMenuView;
import strath.cs308.gizmo.view.interfaces.IPlayView;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class GameView implements IGameView
{
    private StackPane root;
    private EventHandler eventHandler;

    public GameView(BorderPane parent, IPhysicsWorld world)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/game.fxml"));
            this.root = loader.load();

            parent.setCenter(this.root);

            this.eventHandler = new GameController(world, this);

            IIngameMenuView ingameMenuView = new IngameMenuView(this.root, world);
            IPlayView playView = new PlayView(this.root, world);
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
