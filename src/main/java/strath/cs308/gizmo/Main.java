package strath.cs308.gizmo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import strath.cs308.gizmo.view.MainView;
import strath.cs308.gizmo.view.interfaces.IMainView;

public class Main extends Application
{

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
        IMainView view = new MainView(primaryStage);

        primaryStage.show();
    }
}
