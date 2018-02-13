package controller.editor.gizmo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.EditorModel;
import model.gizmo.IGizmo;

public class AbsorberGizmoHandler implements EventHandler<ActionEvent> {

    private EditorModel editorModel;

    public AbsorberGizmoHandler(EditorModel editorModel) {
        this.editorModel = editorModel;
    }

    @Override
    public void handle(ActionEvent event) {
        editorModel.setGizmoGridItem(IGizmo.Type.Triangle);
    }
}
