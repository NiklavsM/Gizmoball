package gui.editor.view;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import gui.Theme;

public abstract class SidePanelMenu extends VBox {

    public SidePanelMenu() {
        setMinWidth(100);
        setSpacing(16);
        setPadding(Theme.DEFAULT_PADDING);

        Label title = new Label(getPanelName());
        getChildren().add(title);
    }

    public abstract String getPanelName();
}
