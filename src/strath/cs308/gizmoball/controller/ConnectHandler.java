package strath.cs308.gizmoball.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.gizmo.IGizmo;
import strath.cs308.gizmoball.utils.Logger;
import strath.cs308.gizmoball.view.EditorView;

import java.util.Optional;

public class ConnectHandler implements EventHandler<ActionEvent> {

    private static final String TAG = "ConnectHandler";
    private IGameModel gameModel;
    private EditorView editorView;
    private ConnectPanelView connectPanelView;

    public ConnectHandler(IGameModel gameModel, EditorView editorView, ConnectPanelView connectPanelView) {
        this.gameModel = gameModel;
        this.editorView = editorView;
        this.connectPanelView = connectPanelView;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        Canvas canvas = editorView.getCanvas();


        switch (((Node) actionEvent.getSource()).getId()) {
            case "connectAChangeButton":
                connectPanelView.setWaitingForKeyStatusA();

                //wait for key press
                editorView.setOnKeyPressed(event -> {
                    connectPanelView.setConnectATextField("KEY " + event.getCode().toString());

                    editorView.setOnKeyPressed(null);
                    canvas.setOnMouseClicked(null);
                });

                canvas.setOnMouseClicked(event -> {
                    double pointX = event.getX() / editorView.getPixelRatioFor(20.0);
                    double pointY = event.getY() / editorView.getPixelRatioFor(20.0);

                    Optional<IGizmo> gizmo = gameModel.getGizmo(pointX, pointY);
                    gizmo.ifPresent(g -> {
                        System.out.println(g);

                        editorView.setOnKeyPressed(null);
                        canvas.setOnMouseClicked(null);
                    });
                });

//                Triangle t = (Triangle) gameModel.getGizmoById("T");
//                t.setAction(args -> {
//                    t.rotate();
//                });
//                t.registerTriggarable(t);
//                Flipper f = (Flipper) gameModel.getGizmoById("RF112");
//                t.registerTriggarable(f);
//                keyHandler.onKeyEventTrigger("key 74.0 down", t);


                Logger.verbose(TAG, "Connected A button clicked");
                break;
            case "connectBChangeButton":

                connectPanelView.setWaitingForKeyStatusB();
                //wait for key press
                editorView.setOnKeyPressed(event -> {
                    System.out.println("PRE " + event.getCode());
                    connectPanelView.setConnectBTextField("KEY " + event.getCode().toString());

                    editorView.setOnKeyPressed(null);
                    canvas.setOnMouseClicked(null);
                });

                canvas.setOnMouseClicked(event -> {
                    double pointX = event.getX() / editorView.getPixelRatioFor(20.0);
                    double pointY = event.getY() / editorView.getPixelRatioFor(20.0);

                    Optional<IGizmo> gizmo = gameModel.getGizmo(pointX, pointY);
                    gizmo.ifPresent(g -> {
                        System.out.println(g);
                        connectPanelView.setConnectBTextField("Gizmo " + g);

                        editorView.setOnKeyPressed(null);
                        canvas.setOnMouseClicked(null);
                    });
                });

                Logger.verbose(TAG, "Connected B button clicked");
                break;
            case "mu2":
                break;
        }
    }
}
