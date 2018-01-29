package strath.cs308.gizmo.view;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import strath.cs308.gizmo.controller.MainController;
import strath.cs308.gizmo.model.interfaces.IPhysicsWorld;
import strath.cs308.gizmo.model.physics.PhysicsWorld;
import strath.cs308.gizmo.view.interfaces.IEditView;
import strath.cs308.gizmo.view.interfaces.IGameView;
import strath.cs308.gizmo.view.interfaces.IMainView;

import java.io.IOException;

public class MainView implements IMainView
{
    private BorderPane root;
    private EventHandler eventHandler;

    public MainView(Stage primaryStage)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/main.fxml"));
            this.root = loader.load();

            primaryStage.setScene(new Scene(this.root));

            IPhysicsWorld world = new PhysicsWorld();

            this.eventHandler = new MainController(this);

            IEditView editView = new EditView(this.root, world);
            IGameView gameView = new GameView(this.root, world);

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
