package strath.cs308.gizmoball.controller.editor.actionbar;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import strath.cs308.gizmoball.GizmoBall;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.UndoRedo;
import strath.cs308.gizmoball.view.IEditorView;

import java.util.ResourceBundle;

public class GamePropertyEventHandler implements EventHandler<ActionEvent> {

    private IGameModel gameModel;
    private IEditorView editorView;
    private ResourceBundle dictionary;

    public GamePropertyEventHandler(IGameModel gameModel, IEditorView editView) {
        this.gameModel = gameModel;
        this.editorView = editView;
        dictionary = ResourceBundle.getBundle("dictionary", GizmoBall.locale);
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        switch (((Node) actionEvent.getSource()).getId()) {
            case "gravity":
                changeGameGravity();
                break;
            case "mu1":
                changeGameFriction1();
                break;
            case "mu2":
                changeGameFriction2();
                break;
        }
    }

    private void changeGameGravity() {
        try {
            if (gameModel.setGravityCoefficient(editorView.getGravityInput())) {

                UndoRedo.INSTANCE.saveState(gameModel);
                editorView.setStatus(dictionary.getString("EDITOR_STATUS_GRAVITY_SET") + editorView.getGravityInput());
            } else {
                editorView.setErrorStatus(dictionary.getString("EDITOR_STATUS_GRAVITY_ERROR"));
            }
        } catch (NumberFormatException e) {
            editorView.setErrorStatus(dictionary.getString("EDITOR_STATUS_GRAVITY_ERROR"));
        }
    }

    private void changeGameFriction1() {
        try {
            if (gameModel.setFrictionM1(editorView.getFriction1Input())) {

                UndoRedo.INSTANCE.saveState(gameModel);
                editorView.setStatus(dictionary.getString("EDITOR_STATUS_FRICTION1_SET") + editorView.getFriction1Input());
            } else {
                editorView.setErrorStatus(dictionary.getString("EDITOR_STATUS_FRICTION1_ERROR"));
            }
        } catch (NumberFormatException e) {
            editorView.setErrorStatus(dictionary.getString("EDITOR_STATUS_FRICTION1_ERROR"));
        }
    }

    private void changeGameFriction2() {
        try {
            if (gameModel.setFrictionM2(editorView.getFriction2Input())) {

                UndoRedo.INSTANCE.saveState(gameModel);
                editorView.setStatus(dictionary.getString("EDITOR_STATUS_FRICTION2_SET") + editorView.getFriction2Input());
            } else {
                editorView.setErrorStatus(dictionary.getString("EDITOR_STATUS_FRICTION2_ERROR"));
            }
        } catch (NumberFormatException e) {
            editorView.setErrorStatus(dictionary.getString("EDITOR_STATUS_FRICTION2_ERROR"));
        }
    }

}
