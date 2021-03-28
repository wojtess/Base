package me.wojtess;

import me.wojtess.modules.ModulesManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        initPrintStreamHook();
        ModulesManager.init();
        while(Thread.getAllStackTraces().keySet().size() != 0) {
            ;
        }
    }

    private static void initPrintStreamHook() {
        System.setOut(new PrintStream(System.out) {
            @Override
            public void println(String in) {
                String date = new SimpleDateFormat("HH:mm:ss").format(new Date(System.currentTimeMillis()));
                super.println("[" + date + "] " + in + Utils.ConsoleColors.RESET);
            }
        });
        System.setErr(new PrintStream(System.err) {
            @Override
            public void println(String in) {
                String date = new SimpleDateFormat("HH:mm:ss").format(new Date(System.currentTimeMillis()));
                super.println("[" + date + "] " + Utils.ConsoleColors.RED_BOLD + in + Utils.ConsoleColors.RESET);
            }
        });
    }

}
