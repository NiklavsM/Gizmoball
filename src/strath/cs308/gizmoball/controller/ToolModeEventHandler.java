package strath.cs308.gizmoball.controller;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import strath.cs308.gizmoball.GizmoBall;
import strath.cs308.gizmoball.controller.strategy.*;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.view.IEditorView;

import java.util.ResourceBundle;

public class ToolModeEventHandler implements EventHandler<MouseEvent> {

    private final IGameModel gameModel;
    private final IEditorView editorView;
    private final ResourceBundle dictionary;
    private final InGameKeyEventHandler keyEventHandler;

    public ToolModeEventHandler(IGameModel gameModel, InGameKeyEventHandler keyEventHandler, IEditorView editorView) {
        this.gameModel = gameModel;
        this.editorView = editorView;
        this.keyEventHandler = keyEventHandler;

        dictionary = ResourceBundle.getBundle("dictionary", GizmoBall.locale);
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        switch (((Node) mouseEvent.getSource()).getId()) {
            case "removeToolButton":

                editorView.setCanvasMode(new RemoveGizmoStrategy(gameModel, keyEventHandler, editorView));
                editorView.setStatus(dictionary.getString("EDITOR_STATUS_REMOVETOOL"));
                break;

            case "rotateToolButton":
                editorView.setCanvasMode(new RotateGizmoStrategy(gameModel, keyEventHandler, editorView));
                editorView.setStatus(dictionary.getString("EDITOR_STATUS_ROTATETOOL"));
                break;

            case "connectToolButton":

                editorView.setCanvasMode(new ConnectGizmoStrategy(gameModel, keyEventHandler, editorView));
                editorView.setStatus(dictionary.getString("EDITOR_STATUS_CONNECTTOOL"));
                break;

            case "moveToolButton":

                editorView.setCanvasMode(new MoveGizmoStrategy(gameModel, keyEventHandler, editorView));
                editorView.setStatus(dictionary.getString("EDITOR_STATUS_MOVETOOL"));
                break;

            case "selectToolButton":
                editorView.setCanvasMode(new SelectGizmoEventHandler(gameModel, editorView));
                editorView.setStatus(dictionary.getString("EDITOR_STATUS_SELECTTOOL"));
                break;

        }
    }
}
