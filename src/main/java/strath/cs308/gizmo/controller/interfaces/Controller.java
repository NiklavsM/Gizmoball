package strath.cs308.gizmo.controller.interfaces;

import javafx.event.EventHandler;
import strath.cs308.gizmo.view.interfaces.View;

public interface Controller extends EventHandler
{
    void setView(View view);
}
