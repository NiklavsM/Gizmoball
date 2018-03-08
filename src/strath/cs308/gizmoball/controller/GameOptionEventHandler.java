package strath.cs308.gizmoball.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.InputMethodEvent;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.view.EditorView;

public class GameOptionEventHandler implements ChangeListener<String> {

    private IGameModel gameModel;
    private EditorView editorView;

    public GameOptionEventHandler(IGameModel gameModel, EditorView editorView) {
        this.gameModel = gameModel;
        this.editorView = editorView;
    }


    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        System.out.println("Handle game option");
        System.out.println();
//        String input = event.getCommitted();

//        try {
//            Double value = Double.parseDouble(input);
//
//            System.out.println(input);
//
//            switch (((Node) event.getSource()).getId()) {
//                case "gravity":
//                    gameModel.setGravityCoEfficient(value);
//                    editorView.setStatus("Value for gravity set to " + value);
//                    System.out.println("WHERE");
//                    break;
//
//                case "friction":
//                    gameModel.setGravityCoEfficient(value);
//                    editorView.setStatus("Value for friction set to " + value);
//                    break;
//            }
//
//
//        } catch (NumberFormatException nfe) {
//            editorView.setStatus("Invalid input");
//        }
    }
}
