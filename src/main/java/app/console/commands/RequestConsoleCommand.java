package app.console.commands;

import app.ImoProject;
import app.console.ConsoleCommand;
import matcher.CommandMatcher;
import request.IRequest;
import request.implementations.ConsoleRequest;
import utils.FileUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.util.regex.Pattern;

public class RequestConsoleCommand extends ConsoleCommand {

    public RequestConsoleCommand() {
        super(Pattern.compile("(get|post|put|patch|delete) [a-zA-Z\\/0-9]+( .+)?", Pattern.CASE_INSENSITIVE), "Runs a command");
    }

    @Override
    public void run(String cmd) {

        String[] options = cmd.split(" ");
        String params = options.length == 3 ? options[2] : "";

        CommandMatcher cm = new CommandMatcher(ImoProject.getContainer());
        IRequest request = new ConsoleRequest(options[0], options[1], params);

        StringWriter sw = new StringWriter();
        cm.execute(request, sw);

        String file = request.headers("output-file"); //output to file or console?
        if( file != null) {
            try {
                FileUtils.writeToFile(sw.toString(), file);
            } catch (IOException e) {
                ImoProject.trace("Error writing in file.");
            }
        } else {
            System.out.println(sw.toString());
        }
    }

}
