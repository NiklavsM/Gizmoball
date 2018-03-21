package strath.cs308.gizmoball.controller.editor.pane;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import strath.cs308.gizmoball.GizmoBall;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.gizmo.IGizmo;
import strath.cs308.gizmoball.model.triggeringsystem.ITriggerable;
import strath.cs308.gizmoball.utils.KeyConverter;
import strath.cs308.gizmoball.utils.Logger;
import strath.cs308.gizmoball.view.ConnectPanelView;
import strath.cs308.gizmoball.view.EditorView;

import java.util.Optional;
import java.util.ResourceBundle;

public class ConnectHandler implements EventHandler<ActionEvent> {

    private static final String TAG = "ConnectHandler";
    private final Canvas canvas;
    private IGameModel gameModel;
    private EditorView editorView;
    private ConnectPanelView connectPanelView;
    private KeyEvent keyEvent;
    private ResourceBundle dictionary;

    public ConnectHandler(IGameModel gameModel, EditorView editorView, ConnectPanelView connectPanelView) {
        this.gameModel = gameModel;
        this.editorView = editorView;
        this.connectPanelView = connectPanelView;
        this.canvas = editorView.getCanvas();
        dictionary = ResourceBundle.getBundle("dictionary", GizmoBall.locale);
    }

    @Override
    public void handle(ActionEvent actionEvent) {

        switch (((Node) actionEvent.getSource()).getId()) {
            case "connectAChangeButton":
                connectionA();
                break;
            case "connectBChangeButton":
                connectionB();
                break;
            case "mu2":
                break;
        }
    }

    private void connectionA() {
        //wait for keyEvent press
        connectPanelView.setWaitingForKeyStatusA();

        editorView.setOnKeyPressed(event -> {
            connectPanelView.setConnectATextField("KEY " + event.getCode().toString());
            keyEvent = event;
            cancelListening();
        });
    }

    private void connectionB() {

        //wait for keyEvent press
        connectPanelView.setWaitingForKeyStatusB();

        editorView.setOnKeyPressed(event -> {
            connectPanelView.setConnectBTextField("KEY " + event.getCode().toString());
            cancelListening();
        });

        canvas.setOnMouseClicked(event -> {

            Optional<IGizmo> gizmo = findGizmo(event);
            gizmo.ifPresent(g -> {

                if (g instanceof ITriggerable) {
                    connectPanelView.setConnectBTextField("Gizmo " + g);
                    String keyStr = KeyConverter.getKeyCode(keyEvent);
                    ITriggerable g1 = (ITriggerable) g;
                    g1.addActionTrigger("key " + keyEvent.getCode().impl_getCode() + ".0 down");
                    g1.addActionTrigger("key " + keyEvent.getCode().impl_getCode() + ".0 up");

                } else {
                    editorView.setErrorStatus(dictionary.getString("EDITOR_STATUS_CONNECT_NOTTRIGGERABLE_ERROR"));
                }

                cancelListening();
            });
        });
    }

    private Optional<IGizmo> findGizmo(MouseEvent event) {
        double pointX = event.getX() / editorView.getPixelRatioFor(20.0);
        double pointY = event.getY() / editorView.getPixelRatioFor(20.0);
        return gameModel.getGizmo(pointX, pointY);
    }

    private void cancelListening() {
        editorView.setOnKeyPressed(null);
        canvas.setOnMouseClicked(null);
    }
}
