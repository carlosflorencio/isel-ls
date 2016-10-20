package app.console;

import app.console.commands.ListenConsoleCommand;
import app.console.commands.OptionConsoleCommand;
import app.console.commands.RequestConsoleCommand;

public class ConsoleCommandFactory {
    private static ConsoleCommand[] commands = { //add commands here
            new ListenConsoleCommand(),
            new OptionConsoleCommand(),
            new RequestConsoleCommand()
    };

    public static boolean parse(String cmd) {
        for (ConsoleCommand command : commands) {
            if (command.match(cmd)) {
                command.run(cmd);
                return true;
            }
        }
        return false;
    }

    public static ConsoleCommand[] getCommands() {
        return commands;
    }
}
