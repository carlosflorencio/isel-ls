package app.console.commands;

import app.RegisterCommands;
import app.console.ConsoleCommand;
import commands.ICommand;

import java.util.regex.Pattern;

public class OptionConsoleCommand extends ConsoleCommand {

    public OptionConsoleCommand() {
        super(Pattern.compile("OPTION \\/", Pattern.CASE_INSENSITIVE), "Start HTTP Server");
    }

    @Override
    public void run(String cmd) {
        for (ICommand command : RegisterCommands.getCommands()) {
            System.out.println(command.getMethod() + " - " + command.getRoute() + " - " + command.getDescription());
        }
    }
}
