package strath.cs308.gizmo.model.physics;

import strath.cs308.gizmo.model.helper.FlipperDirection;

public class Flipper extends PhysicsBody
{
    private final FlipperDirection direction;

    public Flipper(FlipperDirection direction)
    {
        this.direction = direction;
    }
}
