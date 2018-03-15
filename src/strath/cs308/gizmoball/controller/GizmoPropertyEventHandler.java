package strath.cs308.gizmoball.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;

import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.view.IEditorView;

public class GizmoPropertyEventHandler implements EventHandler<ActionEvent>{

    private IGameModel gameModel;
    private IEditorView editorView;

    public GizmoPropertyEventHandler(IGameModel gameModel, IEditorView editorView) {
        this.gameModel = gameModel;
        this.editorView = editorView;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        switch (((Node) actionEvent.getSource()).getId()) {
       
        }
    }

    private void changeMovableVelocityRadian() {
    }  

    private void changeMovableVelocityX() {
       
    }

    private void changeMovableVelocityY() {
    }
}
