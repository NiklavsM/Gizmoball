package model;

public interface ITriggerable {

    enum Event {
        KEY_K, KEY_L
    }

    void trigger(Event event);

}
