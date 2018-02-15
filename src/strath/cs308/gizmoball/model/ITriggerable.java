package strath.cs308.gizmoball.model;

public interface ITriggerable {

    enum Event {
        KEY_K, KEY_L;
    }

    void trigger(Event event);

}
