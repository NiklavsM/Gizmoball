package strath.cs308.gizmo.model.physics;


import physics.Angle;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;
import strath.cs308.gizmo.model.interfaces.IPhysicsBody;
import strath.cs308.gizmo.model.interfaces.IPhysicsWorld;

import java.util.List;
import java.util.Observable;
import java.util.Vector;

public class PhysicsWorld extends Observable implements IPhysicsWorld
{
    private List<PhysicsBody> bodies;

    public PhysicsWorld()
    {
        Vect vector = new Vect(Angle.DEG_90, 2);
        this.bodies = new Vector<>();
    }

    
}
