package strath.cs308.gizmo.controller;

import javafx.event.Event;
import strath.cs308.gizmo.controller.interfaces.Controller;
import strath.cs308.gizmo.view.MainView;
import strath.cs308.gizmo.view.interfaces.IMainView;

public class MainController implements Controller
{
    private IMainView view;

    public MainController(MainView view)
    {
        this.view = view;
    }

    @Override
    public void handle(Event event)
    {

    }
}
