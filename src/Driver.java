import gui.editor.view.GizmoEditorView;
import gui.game.view.GizmoView;
import javafx.application.Application;

public class Driver {

    public static void main(String[] args) {

        System.out.println("Gizmoball!!");
//        Application.launch(GizmoEditorView.class, args);
        Application.launch(GizmoView.class, args);
    }
}