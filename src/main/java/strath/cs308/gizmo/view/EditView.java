package strath.cs308.gizmo.view;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import strath.cs308.gizmo.controller.EditController;
import strath.cs308.gizmo.controller.editstates.AddAbsorber;
import strath.cs308.gizmo.controller.editstates.AddGizmo;
import strath.cs308.gizmo.controller.editstates.DeleteGizmo;
import strath.cs308.gizmo.controller.editstates.RotateGizmo;
import strath.cs308.gizmo.model.interfaces.IPhysicsWorld;
import strath.cs308.gizmo.view.helper.ShapeFactory;

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

            ShapeFactory factory = new ShapeFactory();

            EventHandler<MouseEvent> event1 = new AddAbsorber();
            EventHandler<MouseEvent> event2 = new AddGizmo();
            EventHandler<MouseEvent> event3 = new DeleteGizmo();
            EventHandler<MouseEvent> event4 = new RotateGizmo();


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
