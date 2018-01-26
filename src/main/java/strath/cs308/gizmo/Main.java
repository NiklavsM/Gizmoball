package strath.cs308.gizmo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import strath.cs308.gizmo.controller.interfaces.Controller;
import strath.cs308.gizmo.model.interfaces.IPhysicsWorld;
import strath.cs308.gizmo.model.physics.PhysicsWorld;
import strath.cs308.gizmo.view.MainView;
import strath.cs308.gizmo.view.interfaces.View;

public class Main extends Application
{

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
        View view = new MainView();

        Scene scene = new Scene(view.getParent());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
