package model;

import java.util.List;
import java.util.Observer;

public interface IGameModel {

    List<IGizmo> getGizmos();

    void addObserver(Observer observer);

    void deleteObserver(Observer observer);

    void addGizmo(IGizmo gizmo);

    void tick(double time);
}
