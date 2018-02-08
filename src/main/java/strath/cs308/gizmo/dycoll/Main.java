package strath.cs308.gizmo.dycoll;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import mit.physics.Vect;
import strath.cs308.gizmo.dycoll.model.Ball;
import strath.cs308.gizmo.dycoll.model.IPhysicalWorld;
import strath.cs308.gizmo.dycoll.model.PhysicalWorld;
import strath.cs308.gizmo.dycoll.view.DebugView;

import java.util.Timer;
import java.util.TimerTask;

public class Main extends Application
{

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {

        Ball ball = new Ball(10, 10, 1);
        ball.setVelocity(new Vect(1, 1));

        IPhysicalWorld world = new PhysicalWorld();
        world.getBodies().add(ball);
        DebugView view = new DebugView(primaryStage, world);

        Timer timer = new Timer();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                world.tick(1 / 60.0);
            }
        }, 0, 1000 /60);

        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.show();
    }
}
