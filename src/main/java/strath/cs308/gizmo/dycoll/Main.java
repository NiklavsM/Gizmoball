package strath.cs308.gizmo.dycoll;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import strath.cs308.gizmo.dycoll.model.IPhysicalWorld;
import strath.cs308.gizmo.dycoll.model.PhysicalWorld;
import strath.cs308.gizmo.dycoll.view.DebugView;

public class Main extends Application
{

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
        IPhysicalWorld world = new PhysicalWorld();
        DebugView view = new DebugView(primaryStage, world);

        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.show();
    }
}
