import javafx.application.Application;
import view.game.GizmoView;

public class Driver {

    public static void main(String[] args) {

        System.out.println("Gizmoball!!");
//        Application.launch(GizmoEditorView.class, args);
        Application.launch(GizmoView.class, args);
    }
}