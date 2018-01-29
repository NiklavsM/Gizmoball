package strath.cs308.gizmo.controller;

import javafx.event.Event;
import strath.cs308.gizmo.controller.interfaces.Controller;
import strath.cs308.gizmo.model.helper.SaveHandler;
import strath.cs308.gizmo.model.interfaces.IPhysicsWorld;
import strath.cs308.gizmo.model.interfaces.ISaveHandler;
import strath.cs308.gizmo.view.interfaces.IEditView;

public class EditController implements Controller
{
    private IPhysicsWorld world;
    private IEditView view;


    public EditController(IPhysicsWorld world, IEditView view)
    {
        this.world = world;
        this.view = view;
    }

    @Override
    public void handle(Event event)
    {
        ISaveHandler saveHandler = new SaveHandler();
    }
}
