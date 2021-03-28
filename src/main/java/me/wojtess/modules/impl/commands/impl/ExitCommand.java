package me.wojtess.modules.impl.commands.impl;

import me.wojtess.modules.impl.commands.Command;

public class ExitCommand extends Command {

    public ExitCommand() {
        super("exit", "exiting program");
    }

    @Override
    public void onCommand(String[] args) {
        System.exit(0);
    }
}
