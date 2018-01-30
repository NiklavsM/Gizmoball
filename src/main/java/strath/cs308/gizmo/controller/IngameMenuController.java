package strath.cs308.gizmo.controller;


import javafx.event.Event;
import javafx.event.EventHandler;
import strath.cs308.gizmo.model.helper.SaveHandler;
import strath.cs308.gizmo.model.interfaces.IPhysicsWorld;
import strath.cs308.gizmo.model.interfaces.ISaveHandler;
import strath.cs308.gizmo.view.interfaces.IIngameMenuView;

public class IngameMenuController implements EventHandler
{
    private IIngameMenuView view;
    private IPhysicsWorld world;

    public IngameMenuController(IIngameMenuView view, IPhysicsWorld world)
    {
        this.view = view;
        this.world = world;
    }

    @Override
    public void handle(Event event)
    {
        ISaveHandler saveHandler = new SaveHandler();
    }
}
