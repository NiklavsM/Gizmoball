package model;


import gui.Theme;
import javafx.scene.paint.Color;
import model.gizmo.IGizmo;
import physics.Circle;
import physics.Vect;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Ball implements IGizmo {

    private Vect velocity;
    private double radius;
    private double xpos;
    private double ypos;
    private Color colour;

    // x, y coordinates and x,y velocity
    public Ball(double x, double y, double xv, double yv) {
        xpos = x; // Centre coordinates
        ypos = y;
        colour = Theme.Colors.WHITE;
        velocity = new Vect(xv, yv);
        radius = 0.25;
    }

    public Vect getVelo() {
        return velocity;
    }

    public void setVelo(Vect v) {
        velocity = v;
    }

    public double getRadius() {
        return radius;
    }

    public Circle getCircle() {
        // Walls are the enclosing Rectangle - defined by top left corner and bottom
        // right
        return new Circle(xpos, ypos, radius);

    }

    // Ball specific methods that deal with double precision.
    public double getExactX() {
        return xpos;
    }

    public double getExactY() {
        return ypos;
    }

    public void setExactX(double x) {
        xpos = x;
    }

    public void setExactY(double y) {
        ypos = y;
    }

    public Color getColour() {
        return colour;
    }

    public Set<Dot> getDot() {
        return null;
    }

    @Override
    public List<Dot> getDots() {
        List<Dot> dot = new LinkedList<>();
        dot.add(new Dot(getCircle().getCenter().x(),
                getCircle().getCenter().y(),
                getCircle().getRadius()));
        return dot;
    }

    @Override
    public Type getType() {
        return null;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public void rotate() {

    }
}
