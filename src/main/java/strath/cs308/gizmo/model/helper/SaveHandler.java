package strath.cs308.gizmo.model.helper;

import strath.cs308.gizmo.model.interfaces.ISaveHandler;
import strath.cs308.gizmo.model.physics.*;

import java.util.List;

public class SaveHandler implements ISaveHandler
{
    private List<PhysicsBody> bodies;

    public void load()
    {
        Ball ball = new Ball();
        Circle circle = new Circle();
        Rectangle rectangle = new Rectangle();
        Flipper flipper1 = new Flipper();
    }
}
