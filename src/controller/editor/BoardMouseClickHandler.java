package controller.editor;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import model.Constants;
import model.EditorModel;
import model.IGameModel;
import model.gizmo.*;


public class BoardMouseClickHandler implements EventHandler<MouseEvent> {

    private EditorModel editorModel;
    private IGameModel gameModel;
    private int squareY;
    private int squareX;

    public BoardMouseClickHandler(EditorModel editorModel, IGameModel gameModel) {
        this.editorModel = editorModel;

        this.gameModel = gameModel;
    }

    @Override
    public void handle(MouseEvent event) {
        System.out.println("Clicked on " + event.getX() + " " + event.getY());

        squareX = (int) Math.floor(event.getX() / Constants.pxPerL);
        squareY = (int) Math.floor(event.getY() / Constants.pxPerL);

        System.out.println("In L " + squareX + " " + squareY);

        //TODO: Clean this up

        EditorModel.Mode selectedAction = editorModel.getSelectedAction();

        switch (selectedAction) {
            case ADD:
                addMode();
                break;
            case ROTATE:
                rotateMode();
                break;
        }


        // TODO switch depending on mode
    }

    private void rotateMode() {
        if (gameModel.isOccupied(squareX, squareY)) {
            IEntity.Type gizmoType = editorModel.getGizmoGridItem();

            IEntity gizmo = gameModel.getGizmo(squareX, squareY);
            gameModel.rotate(gizmo.getId());
        }
    }

    private void addMode() {
        if (!gameModel.isOccupied(squareX, squareY)) {
            IEntity.Type gizmoType = editorModel.getGizmoGridItem();

            IEntity gizmo = null;

            switch (gizmoType) {
                case Square:
                    gizmo = new Square(squareX, squareY);
                    break;
                case Triangle:
                    gizmo = new Triangle(squareX, squareY);
                    break;
                case Circle:
                    gizmo = new CircleGizmo(squareX, squareY);
                    break;
            }

            gameModel.addEntity(gizmo);
        }
    }
}
