package strath.cs308.gizmo.controller;

import strath.cs308.gizmo.model.helper.SaveHandler;
import strath.cs308.gizmo.model.interfaces.ISaveHandler;
import strath.cs308.gizmo.view.MainView;

public class MainController
{
    private MainView view;

    public void setView(MainView view)
    {
        this.view = view;


        ISaveHandler handler = new SaveHandler();

    }
}
