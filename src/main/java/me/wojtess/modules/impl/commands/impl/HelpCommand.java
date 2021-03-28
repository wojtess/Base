package me.wojtess.modules.impl.commands.impl;

import me.wojtess.Utils;
import me.wojtess.modules.ModulesManager;
import me.wojtess.modules.impl.commands.Command;
import me.wojtess.modules.impl.commands.CommandsModule;

public class HelpCommand extends Command {

    public HelpCommand() {
        super("help","show all commands");
    }

    @Override
    public void onCommand(String[] args) {
        CommandsModule module = ModulesManager.getModule(CommandsModule.class);
        if(module != null) {
            for (Command command : module.getCommands()) {
                System.out.println(Utils.ConsoleColors.PURPLE + command.getCmd() + Utils.ConsoleColors.BLUE_BRIGHT + " - " + Utils.ConsoleColors.PURPLE + command.getComment());
            }
        }
    }
}
