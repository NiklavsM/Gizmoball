package controller.editor.gizmo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.EditorModel;
import model.gizmo.IEntity;

public class FlipperGizmoHandler implements EventHandler<ActionEvent> {

    private EditorModel editorModel;

    public FlipperGizmoHandler(EditorModel editorModel) {
        this.editorModel = editorModel;
    }

    @Override
    public void handle(ActionEvent event) {
        editorModel.setGizmoGridItem(IEntity.Type.FLIPPER);
    }
}
