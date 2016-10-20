package app.console;

import java.util.regex.Pattern;

public abstract class ConsoleCommand implements IConsoleCommand {
    protected Pattern regex;
    protected String description;

    public ConsoleCommand(Pattern regex, String desc) {
        this.regex = regex;
        this.description = desc;
    }

    public boolean match(String str) {
        return regex.matcher(str).matches();
    }

    public abstract void run(String cmd);

    public String[] parse(String str) {
        return str.split(" ");
    }

    public String getDescription() {
        return description;
    }
}
