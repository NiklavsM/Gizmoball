package controller.game;

import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;

public class ExitGameHandler implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent event) {
    	
    	Alert closeConfirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to quit the game?");
        Button exitButton = (Button) closeConfirmation.getDialogPane().lookupButton(
                ButtonType.OK
        );
        exitButton.setText("Exit");
        closeConfirmation.setHeaderText("Confirm Exit?");
        closeConfirmation.initModality(Modality.APPLICATION_MODAL);

        Optional<ButtonType> closeResponse = closeConfirmation.showAndWait();
        if (ButtonType.OK.equals(closeResponse.get())) {
            System.exit(0);
        }
    }
}