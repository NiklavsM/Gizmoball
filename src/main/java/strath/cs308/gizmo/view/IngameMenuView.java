package strath.cs308.gizmo.view;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import strath.cs308.gizmo.controller.IngameMenuController;
import strath.cs308.gizmo.model.interfaces.IPhysicsWorld;
import strath.cs308.gizmo.view.interfaces.IIngameMenuView;

import java.io.IOException;

public class IngameMenuView implements IIngameMenuView
{
    private Pane root;
    private EventHandler controller;

    public IngameMenuView(StackPane root, IPhysicsWorld world)
    {
        try
        {
            this.root = FXMLLoader.load(this.getClass().getResource("/ingameMenu.fxml"));

            this.controller = new IngameMenuController(this, world);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
