package commands.get;

import commands.Command;

public abstract class GetCommand extends Command {

    /**
     * Creates a get type command
     * @param route Command route
     * @param description Command description
     */
	public GetCommand(String route, String desc) {
        super(route, "GET", desc);
	}

}
