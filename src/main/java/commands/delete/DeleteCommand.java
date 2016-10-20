package commands.delete;

import commands.AuthCommand;

public abstract class DeleteCommand extends AuthCommand {

    /**
     * Creates a delete type command
     * @param route Command route
     * @param description Command description
     */
	public DeleteCommand(String route, String description) {
		super(route, "DELETE", description);
	}

}
