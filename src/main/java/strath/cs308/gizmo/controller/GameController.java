package strath.cs308.gizmo.controller;

import javafx.event.Event;
import strath.cs308.gizmo.controller.interfaces.Controller;
import strath.cs308.gizmo.model.helper.SaveHandler;
import strath.cs308.gizmo.model.interfaces.IPhysicsWorld;
import strath.cs308.gizmo.model.interfaces.ISaveHandler;
import strath.cs308.gizmo.view.GameView;
import strath.cs308.gizmo.view.interfaces.IGameView;

public class GameController implements Controller
{
    private IPhysicsWorld world;
    private IGameView view;

    public GameController(IPhysicsWorld world, GameView view)
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
