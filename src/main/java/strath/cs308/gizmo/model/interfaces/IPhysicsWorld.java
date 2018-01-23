package strath.cs308.gizmo.model.interfaces;

import strath.cs308.gizmo.view.GameView;

import java.util.Observer;

public interface IPhysicsWorld
{
    void addObserver(Observer gameView);
}
