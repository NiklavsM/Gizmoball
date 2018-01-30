package strath.cs308.gizmo.model.physics;


import java.util.List;
import java.util.Observable;
import java.util.Vector;

public class PhysicsWorld extends Observable
{
    private List<PhysicsBody> bodies;

    public PhysicsWorld()
    {
        this.bodies = new Vector<>();
    }

}
