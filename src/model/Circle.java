package model;


import physics.Vect;

public class Circle {
    private physics.Circle circle;

    public Circle(double x, double y, double radius) {
        circle = new physics.Circle(x, y, radius);
    }

    public double getX() {
        return circle.getCenter().x();
    }

    public double getY() {
        return circle.getCenter().y();
    }

    public Vect getCenter() {
        return circle.getCenter();
    }
    public double getRadius(){
        return circle.getRadius();
    }
}
