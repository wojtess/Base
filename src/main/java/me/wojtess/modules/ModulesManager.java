package me.wojtess.modules;

import me.wojtess.modules.impl.DebugModule;
import me.wojtess.modules.interfaces.PreInitialization;
import me.wojtess.modules.interfaces.PostInitialization;
import me.wojtess.modules.interfaces.Worker;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class ModulesManager {

    private static ArrayList<Module> listModules = new ArrayList<>();

    public static void init() {
        Reflections reflections = new Reflections();
        ArrayList<Class<? extends Module>> modules = new ArrayList<>(reflections.getSubTypesOf(Module.class));
        ArrayList<Module> tempModulesList = new ArrayList<>();
        for (Class<? extends Module> clazz : modules) {
            Module module;
            try {
                module = clazz.getConstructor().newInstance();
            } catch (NoSuchMethodException ex) {
                System.err.println("Module \"" + clazz.getSimpleName() + "\" does not have a valid constructor! (Error code:5)");
                break;
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException ex1) {
                System.err.println("Error while making new instance! (Error code:6)");
                ex1.printStackTrace();
                break;
            }
            tempModulesList.add(module);
        }

        tempModulesList.sort((o1, o2) -> {
            if (o1.getPriority() < o2.getPriority()) return 1;
            if (o1.getPriority() > o2.getPriority()) return -1;
            return 0;
        });

        for (Module module : tempModulesList) {
            if(module instanceof PreInitialization) {
                if (isToggledModule(DebugModule.class))
                    System.out.println("Initializing module \"" + module.getName() + "\"!");
                try {
                    ((PreInitialization)module).preInit();
                } catch (Throwable throwable) {
                    if (isToggledModule(DebugModule.class)) {
                        System.err.println("While initializing \"" + module.getName() + "\" occurred error! (Error code:7)");
                        throwable.printStackTrace();
                    }
                }
                if (isToggledModule(DebugModule.class))
                    System.out.println("Module \"" + module.getName() + "\" initialized successively!");
            }
            listModules.add(module);
        }

        for (Module module : tempModulesList) {
            if(module instanceof PostInitialization) {
                if (isToggledModule(DebugModule.class))
                    System.out.println("Post initializing module \"" + module.getName() + "\"!");
                try {
                    ((PostInitialization)module).postInit();
                } catch (Throwable throwable) {
                    if (isToggledModule(DebugModule.class)) {
                        System.err.println("While post initializing \"" + module.getName() + "\" occurred error! (Error code:8)");
                        throwable.printStackTrace();
                    }
                }
                if (isToggledModule(DebugModule.class))
                    System.out.println("Module \"" + module.getName() + "\" post initialized successively!");
            }
            if(module instanceof Worker) {
                module.startThread();
            }
        }
    }

    public static Module getModule(String name) {
        for (Module module : listModules) {
            if(module.getName().equalsIgnoreCase(name)) {
                return module;
            }
        }
        return null;
    }

    public static ArrayList<Module> getModules() {
        return listModules;
    }

    public static <T> T getModule(Class<T> module) {
        for (Module module1 : listModules) {
            if(module1.getClass().equals(module)) {
                return (T) module1;
            }
        }
        return null;
    }

    public static boolean isToggledModule(Class<? extends Module> module) {
        Module module1 = getModule(module);
        if(module1 == null) return false;
        return module1.isToggled();
    }
}
