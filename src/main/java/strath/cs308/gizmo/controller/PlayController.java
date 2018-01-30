package strath.cs308.gizmo.controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import strath.cs308.gizmo.model.interfaces.IPhysicsWorld;
import strath.cs308.gizmo.view.interfaces.IPlayView;

public class PlayController implements EventHandler
{
    private IPlayView view;
    private IPhysicsWorld physicsWorld;

    public PlayController(IPlayView view, IPhysicsWorld physicsWorld)
    {
        this.view = view;
        this.physicsWorld = physicsWorld;
    }

    @Override
    public void handle(Event event)
    {

    }
}
