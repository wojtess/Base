package me.wojtess.modules.impl.commands;

public abstract class Command {

    private String cmd;
    private String comment;

    public Command(String cmd,String comment) {
        this.cmd = cmd;
        this.comment = comment;
    }

    public abstract void onCommand(String[] args);

    public String getCmd() {
        return cmd;
    }

    public String getComment() {
        return comment;
    }
}
