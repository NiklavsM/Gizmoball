package strath.cs308.gizmo.dycoll.model;


import java.util.List;
import java.util.Observer;

public interface IPhysicalWorld
{
    List<IPhysicalBody> getBodies();
    void addObserver(Observer observer);
    void deleteObserver(Observer observer);
}
