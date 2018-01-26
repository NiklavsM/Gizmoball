package strath.cs308.gizmo.model.helper;

import strath.cs308.gizmo.model.physics.Ball;
import strath.cs308.gizmo.model.physics.Circle;
import strath.cs308.gizmo.model.physics.Flipper;
import strath.cs308.gizmo.model.physics.Rectangle;

public class PhysicsBodyFactory
{
    public PhysicsBodyFactory()
    {

        Ball ball = new Ball();
        Circle circle = new Circle();
        Rectangle rectangle = new Rectangle();
        Flipper flipper1 = new Flipper(FlipperDirection.LEFT);
    }
}
