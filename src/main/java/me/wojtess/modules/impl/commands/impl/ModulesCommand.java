package me.wojtess.modules.impl.commands.impl;

import me.wojtess.Utils;
import me.wojtess.modules.Module;
import me.wojtess.modules.ModulesManager;
import me.wojtess.modules.impl.commands.Command;

public class ModulesCommand extends Command {

    public ModulesCommand() {
        super("modules", "modules list/enable/disable/toggle <module>");
    }

    @Override
    public void onCommand(String[] args) {
        if(args.length > 0) {
            String cmd = args[0].toLowerCase();
            String moduleName= "";
            if(args.length > 1) {
                moduleName = args[1];
            }
            switch (cmd) {
                case "list":
                    for (Module module : ModulesManager.getModules()) {
                        if(module.isToggled()) {
                            System.out.println(Utils.ConsoleColors.GREEN + " " + module.getName());
                        } else {
                            System.out.println(Utils.ConsoleColors.RED + " " + module.getName());
                        }
                    }
                    break;
                case "enable":
                    Module module = ModulesManager.getModule(moduleName);
                    if(module != null) {
                        if(!module.isCanToggle()) {
                            System.out.println("Cant enable this module!");
                            break;
                        }
                        module.setToggled(true);
                        System.out.println("Enabled module!");
                    } else {
                        System.out.println("Module \"" + moduleName + "\" dont exist!");
                    }
                    break;
                case "disable":
                    module = ModulesManager.getModule(moduleName);
                    if(module != null) {
                        if(!module.isCanToggle()) {
                            System.out.println("Cant disable this module!");
                            break;
                        }
                        module.setToggled(false);
                        System.out.println("Disabled module!");
                    } else {
                        System.out.println("Module \"" + moduleName + "\" dont exist!");
                    }
                    break;
                case "toggle":
                    module = ModulesManager.getModule(moduleName);
                    if(module != null) {
                        if(!module.isCanToggle()) {
                            System.out.println("Cant toggle this module!");
                            break;
                        }
                        module.toggle();
                        System.out.println("Toggled module!");
                    } else {
                        System.out.println("Module \"" + moduleName + "\" dont exist!");
                    }
                    break;
            }
        } else {
            System.out.println("First argument is null! (list/enable/disable/toggle <module>)");
        }
    }
}
