package model;

public interface ITriggerable {

    enum Event {
        KEYBOARD_SPACE("space");

        private final String param;

        Event(String param) {
            this.param = param;
        }

        public String getParam() {
            return param;
        }
    }

    void trigger(Event event);

}
