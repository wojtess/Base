package me.wojtess.modules.impl.event;

import me.wojtess.modules.Module;
import me.wojtess.modules.ModulesManager;
import me.wojtess.modules.impl.DebugModule;
import me.wojtess.modules.interfaces.PostInitialization;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class EventsModule extends Module implements PostInitialization {

    private ArrayList<Module> modules = new ArrayList<>();

    public EventsModule() {
        super("EventsModule", 90, false, true, false);
    }

    @Override
    public void postInit() {
        Reflections reflections = new Reflections();
        ArrayList<Class<?>> clazzes = new ArrayList<>(reflections.getTypesAnnotatedWith(EventListener.class));
        for (Class<?> clazz : clazzes) {
            if(clazz.getSuperclass().equals(Module.class)) {
                for (Module module : ModulesManager.getModules()) {
                    if(module.getClass().equals(clazz)) {
                        modules.add(module);
                    }
                }
            }
        }
    }

    public void callEvent(Event event) {
        for (Module module : modules) {
            if(module.isToggled()) {
                for (Method method : module.getClass().getDeclaredMethods()) {
                    if (method.getParameterTypes().length == 1 && method.getParameterTypes()[0].equals(event.getClass())) {
                        try {
                            method.invoke(module, event);
                        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                            if (ModulesManager.isToggledModule(DebugModule.class)) {
                                System.err.println("Error while passing event \"" + event.getClass().getSimpleName() + "\" to module \"" + module.getName() + "\"! (Error code:9)");
                                ex.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }
}
