package me.wojtess.modules.impl.event.impl;

import me.wojtess.modules.impl.event.Event;
import me.wojtess.modules.impl.event.EventState;

public class CloseEvent extends Event {

    public CloseEvent() {
        super(EventState.PRE);
    }
}
