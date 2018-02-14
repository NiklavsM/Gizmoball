package controller.editor;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.EditorModel;

public class SelectEventHandler implements EventHandler<ActionEvent> {

    private EditorModel editorModel;

    public SelectEventHandler(EditorModel editorModel) {
        this.editorModel = editorModel;
    }

    @Override
    public void handle(ActionEvent event) {
        editorModel.setMode(EditorModel.Mode.SELECT);

    }
}
