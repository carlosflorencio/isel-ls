package commands.post;

import commands.AuthCommand;

public abstract class PostCommand extends AuthCommand {


    /**
     * Creates a post type command
     * @param route Command route
     * @param description Command description
     */
	public PostCommand(String route, String desc) {
        super(route, "POST", desc);
	}
}
