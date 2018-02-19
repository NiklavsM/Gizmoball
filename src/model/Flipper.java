package model;

import model.gizmo.Gizmo;
import model.gizmo.IEntity;
import physics.Angle;
import physics.LineSegment;
import physics.*;

import java.util.List;
import java.util.Set;

    public class Flipper extends Gizmo implements IMovable, ITriggerable {

        enum Movement {
            BACK, FORWARD, STILL
        }

        public enum Orientation {
            LEFT(1)
            , RIGHT(-1);

            private double mult;

            Orientation(double mult) {
                this.mult = mult;
            }

            public double getMult() {
                return mult;
            }
        }


        private Circle startPoint;
        private Circle endPoint;
        private LineSegment connector1;
        private LineSegment connector2;

        private Vect velocity;
        private double movedAngle;
        private Movement movementStatus;
        private Orientation orientation;
        public Flipper(double x, double y, Orientation orientation){
            this(x,y,orientation,generateID());
        }
        public Flipper(double x, double y, Orientation orientation, String id) {
            super(x,y,x,y,id); // Needs to be fixed ASP WHYYY all gizmos need to have 4 cordinates Nik

            double radius = 0.25;
            this.orientation = orientation;

            movementStatus = Movement.STILL;
            movedAngle = Angle.ZERO.radians();

            velocity = new Vect(Angle.DEG_180);

            if (orientation == Orientation.RIGHT) {
                x = (x + 2) - radius ;
            } else {
                x = x + radius;
            }

            startPoint = new Circle(x, y + radius, radius);
            endPoint = new Circle(x, y + radius + 1.5, radius);

            connector1 = new LineSegment(startPoint.getCenter().x() - radius
                    , startPoint.getCenter().y()
                    , endPoint.getCenter().x() - radius
                    , endPoint.getCenter().y());


            connector2 = new LineSegment(startPoint.getCenter().x() + radius
                    , startPoint.getCenter().y()
                    , endPoint.getCenter().x() + radius
                    , endPoint.getCenter().y());

            lines.add(connector1);
            lines.add(connector2);
            circles.add(startPoint);
            circles.add(endPoint);
        }

        public void rotate(Angle angle) {

        }

        @Override
        public IEntity.Type getType() {
            return IEntity.Type.Flipper;
        }

        @Override
        public void move(double time) {
            if (movementStatus != Movement.STILL) {
                double rotationRadian = velocity.angle().radians() * time;

                if ((rotationRadian + movedAngle) > Angle.DEG_90.radians()) {
                    rotationRadian = Angle.DEG_90.radians() - movedAngle;
                }

                Angle rotationAngle = new Angle(rotationRadian);



                Circle rotated = Geometry.rotateAround(endPoint
                        , startPoint.getCenter()
                        , rotationAngle);


                circles.remove(endPoint);
                endPoint = rotated;
                circles.add(endPoint);
                lines.remove(connector1);
                lines.remove(connector2);

                connector1 = Geometry.rotateAround(connector1
                        , startPoint.getCenter()
                        , rotationAngle);
                connector2 = Geometry.rotateAround(connector2
                        , startPoint.getCenter()
                        , rotationAngle);

                lines.add(connector1);
                lines.add(connector2);
                movedAngle += Math.abs(rotationRadian) ;

                if (movedAngle >= Angle.DEG_90.radians()) {

                    switch (movementStatus) {
                        case FORWARD:
                            movementStatus = Movement.BACK;
                            velocity = new Vect(new Angle(Angle.DEG_180.radians() * orientation.getMult()));
                            break;

                        case BACK:
                            movementStatus = Movement.STILL;
                            velocity = new Vect(Angle.ZERO);
                            break;
                    }

                    movedAngle = Angle.ZERO.radians();
                }
            }

        }

        public Vect getVelocity() {
            return velocity;
        }

        public void setVelocity(Vect velocity) {
            this.velocity = new Vect(new Angle(velocity.angle().radians() * orientation.getMult()));
        }

        private void up() {
            if (movementStatus == Movement.BACK && movedAngle != 0) {
                movementStatus = Movement.FORWARD;
                movedAngle = Angle.DEG_90.radians() - movedAngle ;
                velocity = new Vect(new Angle(Angle.DEG_180.radians() * -1 * orientation.getMult()));
            }

            if (movementStatus == Movement.STILL) {
                movedAngle = Angle.ZERO.radians();
                movementStatus = Movement.FORWARD;
                velocity = new Vect(new Angle(Angle.DEG_180.radians() * -1 * orientation.getMult()));
            }
        }

        public Circle getStartPoint() {
            return startPoint;
        }

        @Override
        public void trigger(Event event) {
            if (orientation == Orientation.RIGHT && event == Event.KEY_L){
                up();
            }

            if (orientation == Orientation.LEFT && event == Event.KEY_K){
                up();
            }

        }


        @Override
    public void draw(Set<LineSegment> lines, List<Circle> circles, double x1, double y1, double x2, double y2) {

    }



}
