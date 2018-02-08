package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class GameModel extends Observable implements IGameModel {

    private List<Gizmo> bodies;
    final static int UNIT_L = 1;

    public GameModel() {
        this.bodies = new ArrayList<>();
    }

    @Override
    public List<IGizmo> getGizmos() {
        return new ArrayList<>(bodies);
    }

    public void addGizmo(IGizmo gizmo) {
        bodies.add((Gizmo) gizmo);

        setChanged();
        notifyObservers();
    }

    public void tick(double time) {

        bodies.stream()
                .filter(gizmo -> gizmo instanceof IMovable)
                .forEach(gizmo -> ((IMovable) gizmo).move(time));

        this.setChanged();
        this.notifyObservers();
    }

}
