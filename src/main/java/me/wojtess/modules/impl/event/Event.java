package me.wojtess.modules.impl.event;

import java.util.Objects;

public class Event {

    private EventState state;

    public Event(EventState state) {
        this.state = state;
    }

    public EventState getState() {
        return state;
    }

    public void setState(EventState state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Event{" +
                "state=" + state +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return state == event.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(state);
    }
}
