package strath.cs308.gizmo.controller;

import strath.cs308.gizmo.model.interfaces.IPhysicsWorld;
import strath.cs308.gizmo.view.GameView;

public class GameController
{
    private IPhysicsWorld world;
    private GameView view;

    public void setWorld(IPhysicsWorld world)
    {
        this.world = world;
    }

    public void setView(GameView view)
    {
        this.view = view;
    }
}
