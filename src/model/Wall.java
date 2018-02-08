package model;

import physics.Circle;
import physics.LineSegment;
import physics.Vect;

public class Wall extends PhysicalBody {
    public Wall(double startX, double startY, double endX, double endY) {
        Vect startPoint = new Vect(startX, startY);
        Vect endPoint = new Vect(endX, endY);

        LineSegment lineSegment = new LineSegment(startPoint, endPoint);

        this.lineSegments.add(lineSegment);

        Circle closingCircle1 = new Circle(startPoint, 0);
        Circle closingCircle2 = new Circle(endPoint, 0);

        this.circles.add(closingCircle1);
        this.circles.add(closingCircle2);
    }

    @Override
    public void setVelocity(Vect velocity) {

    }

    @Override
    public void move(double time) {

    }
}
