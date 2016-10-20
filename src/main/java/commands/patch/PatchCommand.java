package commands.patch;

import commands.AuthCommand;

public abstract class PatchCommand extends AuthCommand {
	

    /**
     * Creates a patch type command
     * @param route Command route
     * @param description Command description
     */
	public PatchCommand(String route, String description) {
		super(route, "PATCH", description);
	}

}
