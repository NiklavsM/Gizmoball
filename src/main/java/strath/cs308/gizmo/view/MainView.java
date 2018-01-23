package strath.cs308.gizmo.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import strath.cs308.gizmo.controller.MainController;
import strath.cs308.gizmo.model.helper.SaveHandler;
import strath.cs308.gizmo.model.interfaces.IPhysicsWorld;
import strath.cs308.gizmo.model.interfaces.ISaveHandler;
import strath.cs308.gizmo.model.physics.PhysicsWorld;

import java.io.IOException;

public class MainView
{
    private BorderPane root;

    public MainView()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/main.fxml"));
            this.root = loader.load();

            MainController controller = loader.getController();
            controller.setView(this);

            IPhysicsWorld world = new PhysicsWorld();

            GameView gameView = new GameView(world);
            EditView editView = new EditView(world);


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
