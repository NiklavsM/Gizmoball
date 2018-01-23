package strath.cs308.gizmo.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class EditView
{
    private Pane root;

    public EditView()
    {
        try
        {
            this.root = FXMLLoader.load(this.getClass().getResource("/edit.fxml"));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public Parent getParent()
    {
        return this.root;
    }
}
