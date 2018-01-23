package strath.cs308.gizmo.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import strath.cs308.gizmo.controller.EditController;
import strath.cs308.gizmo.model.interfaces.IPhysicsWorld;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class EditView implements Observer
{
    private Pane root;

    public EditView(IPhysicsWorld world)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/edit.fxml"));
            this.root = loader.load();

            EditController controller = loader.getController();
            controller.setWorld(world);
            controller.setView(this);

            world.addObserver(this);

        } catch (IOException e)
        {
            e.printStackTrace();
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
