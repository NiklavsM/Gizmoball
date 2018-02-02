import gui.game.view.PlayStage;
import javafx.application.Application;
import view.GizmoView;

public class Driver {

    public static void main(String[] args) {

        System.out.println("Welcome to Gizmoball");
        Application.launch(GizmoView.class, args);
    }
}