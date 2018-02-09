package model;

import mit.physics.Angle;
import mit.physics.Circle;
import mit.physics.LineSegment;
import mit.physics.Vect;

public class Wall extends Gizmo {

    public Wall(double startX, double startY, double endX, double endY) {
        super("Wall1");
        Vect startPoint = new Vect(startX, startY);
        Vect endPoint = new Vect(endX, endY);

        LineSegment lineSegment = new LineSegment(startPoint, endPoint);

        this.lines.add(lineSegment);

        Circle closingCircle1 = new Circle(startPoint, 0);
        Circle closingCircle2 = new Circle(endPoint, 0);

        circles.add(closingCircle1);
        circles.add(closingCircle2);
    }


    @Override
    public void rotate(Angle angle) {

    }

    @Override
    public Type getType() {
        return Type.Wall;
    }

}
