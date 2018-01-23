package strath.cs308.gizmo.controller;

import strath.cs308.gizmo.model.interfaces.IPhysicsBody;
import strath.cs308.gizmo.model.interfaces.IPhysicsWorld;
import strath.cs308.gizmo.view.EditView;

public class EditController
{
    private IPhysicsWorld world;
    private EditView view;

    public void setWorld(IPhysicsWorld world)
    {
        this.world = world;
        IPhysicsBody body = this.world.body();
    }

    public void setView(EditView view)
    {
        this.view = view;
    }
}
