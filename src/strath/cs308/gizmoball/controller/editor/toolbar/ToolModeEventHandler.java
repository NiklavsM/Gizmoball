package strath.cs308.gizmoball.controller.editor.toolbar;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import strath.cs308.gizmoball.GizmoBall;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.view.IEditorView;

import java.util.ResourceBundle;

public class ToolModeEventHandler implements EventHandler<MouseEvent> {

    private final IGameModel gameModel;
    private final IEditorView editorView;
    private final ResourceBundle dictionary;

    public ToolModeEventHandler(IGameModel gameModel, IEditorView editorView) {
        this.gameModel = gameModel;
        this.editorView = editorView;

        dictionary = ResourceBundle.getBundle("dictionary", GizmoBall.locale);
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        switch (((Node) mouseEvent.getSource()).getId()) {

            case "removeToolButton":
                editorView.setCanvasMode(new RemoveGizmoStrategy(gameModel, editorView));
                editorView.setStatus(dictionary.getString("EDITOR_STATUS_REMOVETOOL"));
                break;

            case "rotateToolButton":
                editorView.setCanvasMode(new RotateGizmoStrategy(gameModel, editorView));
                editorView.setStatus(dictionary.getString("EDITOR_STATUS_ROTATETOOL"));
                break;

            case "connectToolButton":
                editorView.setCanvasMode(new ConnectGizmoStrategy(gameModel, editorView));
                editorView.setStatus(dictionary.getString("EDITOR_STATUS_CONNECTTOOL"));
                break;

            case "moveToolButton":
                editorView.setCanvasMode(new MoveGizmoStrategy(gameModel, editorView));
                editorView.setStatus(dictionary.getString("EDITOR_STATUS_MOVETOOL"));
                break;

            case "selectToolButton":
                editorView.setCanvasMode(new SelectGizmoEventHandler(gameModel, editorView));
                editorView.setStatus(dictionary.getString("EDITOR_STATUS_SELECTTOOL"));
                break;

        }
    }
}
