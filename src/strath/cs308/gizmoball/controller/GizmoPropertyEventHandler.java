package strath.cs308.gizmoball.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;

import strath.cs308.gizmoball.GizmoBall;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.IMovable;
import strath.cs308.gizmoball.model.UndoRedo;
import strath.cs308.gizmoball.model.gizmo.IGizmo;
import strath.cs308.gizmoball.view.IEditorView;

import java.util.ResourceBundle;


public class GizmoPropertyEventHandler implements EventHandler<ActionEvent> {

    private IEditorView editorView;
    private IGizmo gizmo;
    private ResourceBundle dictionary;

    private IGameModel gameModel;

    public GizmoPropertyEventHandler(IEditorView editorView, IGizmo gizmo, IGameModel gameModel) {
        this.editorView = editorView;
        this.gizmo = gizmo;
        this.gameModel = gameModel;
        dictionary = ResourceBundle.getBundle("dictionary", GizmoBall.locale);
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
        if (gameModel.setGizmoColor(gizmo, editorView.getGizmoColor())) {
            editorView.setStatus(dictionary.getString("EDITOR_STATUS_COLORCHANGED"));
        } else {
            editorView.setErrorStatus(dictionary.getString("EDITOR_STATUS_COLORCHANGED_ERROR"));
        }

    }

    private void changeReflectionCoefficient() {
        try {
            if (gizmo.setReflectionCoefficient(editorView.getReflectionCoefficient())) {
                editorView.setStatus(dictionary.getString("EDITOR_STATUS_GIZMOREFLECTION_SET"));
            } else {
                editorView.setErrorStatus(dictionary.getString("EDITOR_STATUS_GIZMOREFLECTION_ERROR"));
            }
        } catch (NumberFormatException e) {
            editorView.setErrorStatus(dictionary.getString("EDITOR_STATUS_GIZMOREFLECTION_ERROR"));
        }
    }

    private void changeMovableVelocityRadian() {
        IMovable movableGizmo = (IMovable) gizmo;
        try {
            movableGizmo.setVelocityRadian(editorView.getRadianProperty());

            UndoRedo.INSTANCE.saveState(gameModel);
            editorView.setStatus(dictionary.getString("EDITOR_STATUS_VELOCITYRADIAN_SET"));
        } catch (NumberFormatException e) {
            editorView.setErrorStatus(dictionary.getString("EDITOR_STATUS_VELOCITYRADIAN_ERROR"));
        }
    }

    private void changeMovableVelocityX() {
        IMovable movableGizmo = (IMovable) gizmo;
        try {
            movableGizmo.setVelocity(editorView.getXVelocityProperty(), movableGizmo.getVelocityY());

            UndoRedo.INSTANCE.saveState(gameModel);
            editorView.setStatus(dictionary.getString("EDITOR_STATUS_VELOCITYX_SET"));
        } catch (NumberFormatException e) {
            editorView.setErrorStatus(dictionary.getString("EDITOR_STATUS_VELOCITYX_ERROR"));
        }
    }

    private void changeMovableVelocityY() {
        IMovable movableGizmo = (IMovable) gizmo;
        try {
            movableGizmo.setVelocity(movableGizmo.getVelocityX(), editorView.getYVelocityProperty());

            UndoRedo.INSTANCE.saveState(gameModel);
            editorView.setStatus(dictionary.getString("EDITOR_STATUS_VELOCITYY_SET"));
        } catch (NumberFormatException e) {
            editorView.setErrorStatus(dictionary.getString("EDITOR_STATUS_VELOCITYY_ERROR"));
        }
    }
}
