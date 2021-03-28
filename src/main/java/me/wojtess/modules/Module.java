package me.wojtess.modules;

import me.wojtess.modules.impl.DebugModule;
import me.wojtess.modules.interfaces.Worker;

import java.util.Objects;

public abstract class Module {

    private String name;
    private int priority;
    private boolean visible;
    private boolean toggled;
    private boolean canToggle;

    public Module(String name, int priority, boolean visible, boolean toggled, boolean canToggle) {
        this.name = name;
        this.priority = priority;
        this.visible = visible;
        this.toggled = toggled;
        this.canToggle = canToggle;
    }

    public boolean isToggled() {
        return toggled;
    }

    public void setToggled(boolean toggled) {
        if((toggled != this.toggled) && toggled)
        startThread();
        this.toggled = toggled;
    }

    public void startThread() {
        if(this instanceof Worker) {
            Thread thread = new Thread(() -> {
                while (this.isToggled()) {
                    try {
                        ((Worker) this).tick();
                    } catch (Throwable throwable) {
                        if(ModulesManager.isToggledModule(DebugModule.class)) {
                            System.err.println("Error while running tick to module \"" + name + "\"! (Error code:10)");
                            throwable.printStackTrace();
                        }
                    }
                }
            });
            thread.setName("Module Thread - " + name);
            thread.start();
        }
    }

    public void toggle() {
        if(canToggle) {
            setToggled(!isToggled());
        }
    }

    public String getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }

    public boolean isVisible() {
        return visible;
    }

    public boolean isCanToggle() {
        return canToggle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Module module = (Module) o;
        return priority == module.priority &&
                visible == module.visible &&
                toggled == module.toggled &&
                canToggle == module.canToggle &&
                Objects.equals(name, module.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, priority, visible, toggled, canToggle);
    }

    @Override
    public String toString() {
        return "Module{" +
                "name='" + name + '\'' +
                ", priority=" + priority +
                ", visible=" + visible +
                ", toggled=" + toggled +
                ", canToggle=" + canToggle +
                '}';
    }
}
