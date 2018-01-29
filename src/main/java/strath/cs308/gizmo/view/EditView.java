package strath.cs308.gizmo.view;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import strath.cs308.gizmo.controller.EditController;
import strath.cs308.gizmo.controller.editstates.AddGizmo;
import strath.cs308.gizmo.controller.editstates.ConnectGizmo;
import strath.cs308.gizmo.controller.editstates.DeleteGizmo;
import strath.cs308.gizmo.controller.editstates.RotateGizmo;
import strath.cs308.gizmo.model.interfaces.IPhysicsWorld;
import strath.cs308.gizmo.view.helper.ShapeFactory;
import strath.cs308.gizmo.view.interfaces.IEditView;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class EditView implements IEditView, Observer
{
    private Pane root;
    private EventHandler eventHandler;

    public EditView(BorderPane parent, IPhysicsWorld world)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/edit.fxml"));
            this.root = loader.load();

            parent.setCenter(this.root);

            world.addObserver(this);

            this.eventHandler = new EditController(world, this);

            ShapeFactory factory = new ShapeFactory();

            EventHandler<MouseEvent> event1 = new AddGizmo();
            EventHandler<MouseEvent> event2 = new ConnectGizmo();
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
