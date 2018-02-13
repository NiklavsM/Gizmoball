package controller.editor;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import model.Constants;


public class BoardMouseClickHandler implements EventHandler<MouseEvent> {

    @Override
    public void handle(MouseEvent event) {
        System.out.println("Clicked on " + event.getX() + " " + event.getY());
        int squareX = (int) Math.floor(event.getX() / Constants.pxPerL);
        int squareY = (int) Math.floor(event.getY() / Constants.pxPerL);

        //TODO: Get gizmo at position
        System.out.println("In L " + squareX + " " + squareY);

    }
}
