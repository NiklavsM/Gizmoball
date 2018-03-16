package strath.cs308.gizmoball.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;

import strath.cs308.gizmoball.model.gizmo.IGizmo;
import strath.cs308.gizmoball.view.IEditorView;


public class GizmoPropertyEventHandler implements EventHandler<ActionEvent>{

    private IEditorView editorView;
    private IGizmo gizmo;

    public GizmoPropertyEventHandler(IEditorView editorView, IGizmo gizmo) {
        this.editorView = editorView;
        this.gizmo = gizmo;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        switch (((Node) actionEvent.getSource()).getId()) {
            case "xVelocityField":
                changeMovableVelocityX();
                break;

            case "yVelocityField":
                changeMovableVelocityY();
                break;

            case "radianVelocityField":
                changeMovableVelocityRadian();
                break;
        }
    }

    private void changeMovableVelocityRadian() {
        System.out.println("velocity radian");
    }  

    private void changeMovableVelocityX() {
       System.out.println("velocity X");
    }

    private void changeMovableVelocityY() {
        System.out.println("velocity Y");
    }
}