package me.wojtess.modules.impl.commands;

import me.wojtess.modules.Module;
import me.wojtess.modules.ModulesManager;
import me.wojtess.modules.impl.DebugModule;
import me.wojtess.modules.interfaces.PostInitialization;
import me.wojtess.modules.interfaces.PreInitialization;
import me.wojtess.modules.interfaces.Worker;
import org.reflections.Reflections;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class CommandsModule extends Module implements PostInitialization, Worker {

    private BufferedReader reader;
    private ArrayList<Command> commands = new ArrayList<>();

    public CommandsModule() {
        super("CommandsModule", 50, false, true, false);
    }

    @Override
    public void postInit() {
        reader = new BufferedReader(new InputStreamReader(System.in));
        Reflections reflections = new Reflections();
        ArrayList<Class<? extends Command>> clazzes = new ArrayList<>(reflections.getSubTypesOf(Command.class));
        for (Class<? extends Command> clazz : clazzes) {
            Command command;
            try {
                command = clazz.getConstructor().newInstance();
            } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                if(ModulesManager.isToggledModule(DebugModule.class)) {
                    System.err.println("Error while making new instance of command \"" + clazz.getSimpleName() + "\"! (Error code:11)");
                    ex.printStackTrace();
                }
                break;
            }
            if(ModulesManager.isToggledModule(DebugModule.class)) {
                System.out.println("Command \"" + command.getCmd() + "\" initialized!");
            }
            commands.add(command);
        }
    }


    @Override
    public void tick() throws Throwable {
        String line = reader.readLine();
        if(line != null) {
            String cmd = line.split(" ")[0];
            String[] args = new String[] {};
            if(line.length() > cmd.length()) {
                args = line.substring(Math.max(0, cmd.length() + 1)).split(" ");
            }
            Command command = getCommand(cmd);
            if(command != null) {
                command.onCommand(args);
            } else {
                System.out.println("Use \"help\" for help.");
            }
        }
    }

    private Command getCommand(String cmd) {
        for (Command command : commands) {
            if(command.getCmd().equalsIgnoreCase(cmd)) {
                return command;
            }
        }
        return null;
    }

    public ArrayList<Command> getCommands() {
        return commands;
    }
}
