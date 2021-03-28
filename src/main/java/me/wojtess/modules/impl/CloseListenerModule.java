package me.wojtess.modules.impl;

import me.wojtess.modules.Module;
import me.wojtess.modules.ModulesManager;
import me.wojtess.modules.impl.event.EventsModule;
import me.wojtess.modules.impl.event.impl.CloseEvent;
import me.wojtess.modules.interfaces.PostInitialization;

public class CloseListenerModule extends Module implements PostInitialization {

    public CloseListenerModule() {
        super("CloseListenerModule", 0, true, true, false);
    }

    @Override
    public void postInit() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            EventsModule module = ModulesManager.getModule(EventsModule.class);
            if(module != null) {
                module.callEvent(new CloseEvent());
            }
        }));
    }
}
