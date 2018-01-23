package strath.cs308.gizmo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import strath.cs308.gizmo.view.MainView;

public class Main extends Application
{

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
        MainView view = new MainView();

        Scene scene = new Scene(view.getParent());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
