package controller.editor.gizmo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.EditorModel;
import model.gizmo.IGizmo;

public class SquareGizmoListener implements EventHandler<ActionEvent> {

    private EditorModel editorModel;

    public SquareGizmoListener(EditorModel editorModel) {
        this.editorModel = editorModel;
    }


    @Override
    public void handle(ActionEvent event) {
        editorModel.setGizmoGridItem(IGizmo.Type.Square);
    }
}
