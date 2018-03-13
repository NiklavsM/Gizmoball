package strath.cs308.gizmoball.model.gizmo;

import mit.physics.Circle;
import mit.physics.LineSegment;
import strath.cs308.gizmoball.model.triggeringsystem.IAction;
import strath.cs308.gizmoball.model.triggeringsystem.ITriggerable;

public class Square extends Gizmo implements ITriggerable {

    private IAction triggerAction;

    public Square(double x, double y) {
        this(x, y, generateID());
    }

    public Square(double x, double y, String id) {
        super(x, y, x + 1, y + 1, id);
    }

    @Override
    protected void setup(double x1, double y1, double x2, double y2) {
        lines.add(new LineSegment(x1, y1, x1 + 1, y1));
        lines.add(new LineSegment(x1, y1, x1, y1 + 1));
        lines.add(new LineSegment(x1 + 1, y1, x1 + 1, y1 + 1));
        lines.add(new LineSegment(x1, y1 + 1, x1 + 1, y1 + 1));

        circles.add(new Circle(x1, y1, 0));
        circles.add(new Circle(x1 + 1, y1, 0));
        circles.add(new Circle(x1 + 1, y1 + 1, 0));
        circles.add(new Circle(x1, y1 + 1, 0));
    }

    @Override
    public IGizmo.Type getType() {
        return IGizmo.Type.SQUARE;
    }

    @Override
    public void trigger(String triggerEvent) {
        triggerAction.doAction();
    }

    @Override
    public void registerAction(IAction triggerAction) {
        this.triggerAction = triggerAction;
    }
}
