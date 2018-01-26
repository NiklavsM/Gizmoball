package strath.cs308.gizmo.view;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import strath.cs308.gizmo.controller.MainController;
import strath.cs308.gizmo.controller.interfaces.Controller;
import strath.cs308.gizmo.model.helper.SaveHandler;
import strath.cs308.gizmo.model.interfaces.IPhysicsWorld;
import strath.cs308.gizmo.model.interfaces.ISaveHandler;
import strath.cs308.gizmo.model.physics.PhysicsWorld;
import strath.cs308.gizmo.view.interfaces.IMainView;
import strath.cs308.gizmo.view.interfaces.View;

import java.io.IOException;

public class MainView implements IMainView
{
    private BorderPane root;
    private EventHandler eventHandler;

    public MainView()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/main.fxml"));
            this.root = loader.load();

            IPhysicsWorld world = new PhysicsWorld();

            this.eventHandler = new MainController(this);

            View editView = new EditView(world);

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
