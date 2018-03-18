package strath.cs308.gizmoball.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;

import strath.cs308.gizmoball.model.IMovable;
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

            case "reflectionCoeffField":
                changeReflectionCoefficient();

            case "colorPickerPorperty":
                changeGizmoColor();
                break;
        }
    }

    private void changeGizmoColor() {

        gizmo.setColor(editorView.getGizmoColor());
        editorView.refresh();

    }

    private void changeReflectionCoefficient() {
        try {
            gizmo.setReflectionCoefficient(editorView.getReflectionCoefficient());
            editorView.setStatus("Gizmo reflection coefficient set!");
        } catch (NumberFormatException e) {
            editorView.setErrorStatus("Given reflection coefficient value is not acceptable");
        }
    }

    private void changeMovableVelocityRadian() {
        IMovable movableGizmo = (IMovable) gizmo;
        try {
            movableGizmo.setVelocityRadian(editorView.getRadianProperty());
            editorView.setStatus("Gizmo radian velocity set!");
        } catch (NumberFormatException e) {
            editorView.setErrorStatus("Given radian value is not acceptable");
        }
    }

    private void changeMovableVelocityX() {
        IMovable movableGizmo = (IMovable) gizmo;
        try {
            movableGizmo.setVelocity(editorView.getXVelocityProperty(), movableGizmo.getVelocityY());
            editorView.setStatus("Gizmo X velocity set!");
        } catch (NumberFormatException e) {
            editorView.setErrorStatus("Given X velocity value is not acceptable!");
        }
    }

    private void changeMovableVelocityY() {
        IMovable movableGizmo = (IMovable) gizmo;
        try {
            movableGizmo.setVelocity(movableGizmo.getVelocityX(), editorView.getYVelocityProperty());
            editorView.setStatus("Gizmo Y velocity set!");
        } catch (NumberFormatException e) {
            editorView.setErrorStatus("Given Y velocity value is not acceptable!");
        }
    }
}
