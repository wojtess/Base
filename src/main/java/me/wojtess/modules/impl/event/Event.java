package me.wojtess.modules.impl.event;

import java.util.Objects;

public class Event {

    private EventState state;
    private boolean cancelled;

    public Event(EventState state) {
        this.state = state;
        this.cancelled = false;
    }

    public EventState getState() {
        return state;
    }

    public void setState(EventState state) {
        this.state = state;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public String toString() {
        return "Event{" +
                "state=" + state +
                ", cancelled=" + cancelled +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return cancelled == event.cancelled &&
                state == event.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(state);
    }
}
