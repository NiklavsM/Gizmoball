package controller.editor.gizmo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.EditorModel;
import model.gizmo.IEntity;

public class TriangleGizmoHandler implements EventHandler<ActionEvent> {

    private EditorModel editorModel;

    public TriangleGizmoHandler(EditorModel editorModel) {
        this.editorModel = editorModel;
    }

    @Override
    public void handle(ActionEvent event) {
        editorModel.setGizmoGridItem(IEntity.Type.TRIANGLE);
    }
}