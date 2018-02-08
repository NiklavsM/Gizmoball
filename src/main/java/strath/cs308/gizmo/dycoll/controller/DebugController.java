package strath.cs308.gizmo.dycoll.controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import strath.cs308.gizmo.dycoll.model.IPhysicalWorld;
import strath.cs308.gizmo.dycoll.model.IWorldTimer;
import strath.cs308.gizmo.dycoll.model.WorldTimer;


public class DebugController implements EventHandler
{
    private final IPhysicalWorld world;
    private final IWorldTimer timer;

    public DebugController(IPhysicalWorld world)
    {
        this.world = world;
        this.timer = new WorldTimer(this.world);
    }

    @Override
    public void handle(Event event)
    {
        if (event instanceof KeyEvent)
        {
            KeyEvent keyEvent = (KeyEvent) event;

            switch (keyEvent.getCode())
            {
                case SPACE:
                    this.stopStartHearthBeat();
                break;
            }
        }
    }

    private void stopStartHearthBeat()
    {
        this.timer.toggle();
    }
}
