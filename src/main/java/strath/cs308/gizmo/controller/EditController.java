package strath.cs308.gizmo.controller;

import strath.cs308.gizmo.model.interfaces.IPhysicsWorld;
import strath.cs308.gizmo.view.EditView;

public class EditController
{
    private IPhysicsWorld world;
    private EditView view;

    public void setWorld(IPhysicsWorld world)
    {
        this.world = world;
    }

    public void setView(EditView view)
    {
        this.view = view;
    }
}
