package strath.cs308.gizmo.model.helper;

import strath.cs308.gizmo.model.physics.*;

public class PhysicsBodyFactory
{
    public PhysicsBodyFactory()
    {

        Ball ball = new Ball();
        Circle circle = new Circle();
        Rectangle rectangle = new Rectangle();
        Flipper flipper1 = new Flipper(FlipperDirection.LEFT);
        Absorber absorber = new Absorber();
    }
}
