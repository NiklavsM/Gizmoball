package strath.cs308.gizmoball.controller.editor.pane;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.gizmo.IGizmo;
import strath.cs308.gizmoball.model.triggeringsystem.ITriggerable;
import strath.cs308.gizmoball.utils.KeyConverter;
import strath.cs308.gizmoball.utils.Logger;
import strath.cs308.gizmoball.view.ConnectPanelView;
import strath.cs308.gizmoball.view.EditorView;

import java.util.Optional;

public class ConnectHandler implements EventHandler<ActionEvent> {

    private static final String TAG = "ConnectHandler";
    private final Canvas canvas;
    private IGameModel gameModel;
    private EditorView editorView;
    private ConnectPanelView connectPanelView;
    private KeyEvent keyEvent;

    public ConnectHandler(IGameModel gameModel, EditorView editorView, ConnectPanelView connectPanelView) {
        this.gameModel = gameModel;
        this.editorView = editorView;
        this.connectPanelView = connectPanelView;
        this.canvas = editorView.getCanvas();
    }

    @Override
    public void handle(ActionEvent actionEvent) {


        switch (((Node) actionEvent.getSource()).getId()) {
            case "connectAChangeButton":
                connectionA();

                Logger.verbose(TAG, "Connected A button clicked");
                break;
            case "connectBChangeButton":
                connectionB();
                Logger.verbose(TAG, "Connected B button clicked");
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

            char c = event.getCode().toString().toCharArray()[0];
            System.out.println("Character entered " + c);
            System.out.println("Code " + (int) c);

            keyEvent = event;
            System.out.println(KeyConverter.prettify(event));

            cancelListening();
        });

//        canvas.setOnMouseClicked(event -> {
//            Optional<IGizmo> gizmo = findGizmo(event);
//            gizmo.ifPresent(g -> {
//                System.out.println(g);
//
//                cancelListening();
//            });
//        });
    }

    private void connectionB() {

        //wait for keyEvent press
        connectPanelView.setWaitingForKeyStatusB();
        editorView.setOnKeyPressed(event -> {
            System.out.println("PRE " + event.getCode());

            connectPanelView.setConnectBTextField("KEY " + event.getCode().toString());

            cancelListening();
        });

        canvas.setOnMouseClicked(event -> {

            Optional<IGizmo> gizmo = findGizmo(event);
            gizmo.ifPresent(g -> {
                System.out.println(g);

                if (g instanceof ITriggerable) {

                    connectPanelView.setConnectBTextField("Gizmo " + g);

                    String keyStr = KeyConverter.getKeyCode(keyEvent);

                    System.out.println("Key: " + keyStr);
                    ITriggerable g1 = (ITriggerable) g;

                    System.out.println("Key string " + keyStr);
                    g1.addActionTrigger("key " + keyEvent.getCode().impl_getCode() + ".0 down");
                    g1.addActionTrigger("key " + keyEvent.getCode().impl_getCode() + ".0 up");

                } else {
//                            editorView.setStatus()
                    Logger.verbose(TAG, "The Gizmo you are trying to connect can not be triggered");

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
