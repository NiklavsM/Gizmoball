package model;

import java.util.ArrayList;
import java.util.Observable;

import physics.Circle;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;

import javax.swing.Timer;

public class Model extends Observable {

    private ArrayList<VerticalLine> lines;
    private MITBall MITBall;
    private Walls gws;
    private Timer timer;

    public Model() {

        // Ball position (1, 1) in L. Ball velocity (4, 4) L per tick
        MITBall = new MITBall(1, 1, 4, 4);

        // Wall size 20 x 20 L
        gws = new Walls(0, 0, 20, 20);

        // Lines added in Main
        lines = new ArrayList<VerticalLine>();
        setupTimer();
    }

    private void setupTimer() {
        timer = new Timer(50, e -> moveBall());
    }

    public void moveBall() {

        double moveTime = 0.05; // 0.05 = 20 times per second as per Gizmoball

        if (MITBall != null) {
            //Time until collision
            CollisionDetails cd = timeUntilCollision();
            double tuc = cd.getTuc();
            if (tuc > moveTime) {
                // No collision ...
                MITBall = moveBallForTime(MITBall, moveTime);
            } else {
                // We've got a collision in tuc
                MITBall = moveBallForTime(MITBall, tuc);
                // Post collision velocity ...
                MITBall.setVelo(cd.getVelo());

            }

            // Notify observers ... redraw updated view
            this.setChanged();
            this.notifyObservers();
        }

    }

    private MITBall moveBallForTime(MITBall MITBall, double time) {

        double newX = 0.0;
        double newY = 0.0;
        double xVel = MITBall.getVelo().x();
        double yVel = MITBall.getVelo().y();
        newX = MITBall.getExactX() + (xVel * time);
        newY = MITBall.getExactY() + (yVel * time);
        MITBall.setExactX(newX);
        MITBall.setExactY(newY);
        return MITBall;
    }

    private CollisionDetails timeUntilCollision() {
        // Find Time Until Collision and also, if there is a collision, the new speed vector.
        // Create a physics.Circle from Ball
        Circle ballCircle = MITBall.getCircle();
        Vect ballVelocity = MITBall.getVelo();
        Vect newVelo = new Vect(0, 0);

        // Now find shortest time to hit a vertical line or a wall line
        double shortestTime = Double.MAX_VALUE;
        double time = 0.0;

        // Time to collide with 4 walls
        ArrayList<LineSegment> lss = gws.getLineSegments();
        for (LineSegment line : lss) {
            time = Geometry.timeUntilWallCollision(line, ballCircle, ballVelocity);
            if (time < shortestTime) {
                shortestTime = time;
                newVelo = Geometry.reflectWall(line, MITBall.getVelo(), 1.0);
            }
        }

        // Time to collide with any vertical lines
        for (VerticalLine line : lines) {
            LineSegment ls = line.getLineSeg();
            time = Geometry.timeUntilWallCollision(ls, ballCircle, ballVelocity);
            if (time < shortestTime) {
                shortestTime = time;
                newVelo = Geometry.reflectWall(ls, MITBall.getVelo(), 1.0);
            }
        }
        return new CollisionDetails(shortestTime, newVelo);
    }

    public MITBall getMITBall() {
        return MITBall;
    }

    public ArrayList<VerticalLine> getLines() {
        return lines;
    }

    public void addLine(VerticalLine l) {
        lines.add(l);
    }

    public void setBallSpeed(int x, int y) {
        MITBall.setVelo(new Vect(x, y));
    }

    public void startTimer() {
        timer.start();
    }

    public void stopTimer() {
        timer.stop();
    }

    public boolean timerIsRunning() {
        return timer.isRunning();
    }
}
