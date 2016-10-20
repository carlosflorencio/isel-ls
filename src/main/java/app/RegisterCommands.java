package app;

import commands.ICommand;
import commands.container.IContainer;
import commands.delete.DeletePropertiesByIdCommand;
import commands.delete.DeleteRentalsCommand;
import commands.get.*;
import commands.patch.PatchRentalCommand;
import commands.post.PostPropertyCommand;
import commands.post.PostRentalCommand;
import commands.post.PostUserCommand;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RegisterCommands {

    private static Map<String, String> commandsBinds = new HashMap<String, String>();
	private static List<ICommand> commands =  new LinkedList<ICommand>();

    /**
     * Register the commands in the given container
     * @param container Container where commands will be registered
     */
	public static void register(IContainer container) {
        add(new GetUsersCommand("/users"), "get.users");
        add(new GetUserByUsernameCommand("/users/{username}"), "get.userByUsername");
        add(new GetPropertiesCommand("/properties"), "get.properties");
        add(new GetPropertiesByLocationCommand("/properties/location/{location}"), "get.propertiesByLocal");
        add(new GetPropertiesByOwnerCommand("/properties/owner/{owner}"), "get.propertiesByOwner");
        add(new GetPropertiesByTypeCommand("/properties/type/{type}"), "get.propertiesByType");
        add(new GetPropertiesByIdCommand("/properties/details/{pid}"), "get.propertiesById");
        add(new GetRentalByPropertyYearWeekCommand("/properties/{pid}/rentals/{year}/{cw}"), "get.rentalByPropertyYearWeek");
        add(new GetRentalsByPropertiesIdCommand("/properties/{pid}/rentals"), "get.rentalsByPropertiesId");
        add(new GetPropertyByUsernameCommand("/users/{username}/properties/owned"), "get.propertyByUsername");
        add(new GetRentalsByUsernameCommand("/users/{username}/rentals"), "get.rentalsByUsername");
        add(new GetRentalsByPropertyYearCommand("/properties/{pid}/rentals/{year}"), "get.rentalsByPropertyYear");
        add(new PostUserCommand("/users"), "post.user");
        add(new PostPropertyCommand("/properties"), "post.property");
        add(new PostRentalCommand("/properties/{pid}/rentals"), "post.rental");
        add(new PatchRentalCommand("/properties/{pid}/rentals/{year}/{cw}"), "patch.rental");
        add(new DeleteRentalsCommand("/properties/{pid}/rentals/{year}/{cw}"), "delete.rentals");
        add(new DeletePropertiesByIdCommand("/properties/{pid}"), "delete.propertiesById");

        addCommandsToContainer(container);
	}

    private static void addCommandsToContainer(IContainer container) {
        for (ICommand command : commands) {
            container.add(command);
        }
    }

    /**
     * Add a command and the bind to the containers
     * @param cmd Command to add
     * @param bind Bind to associate to that command
     */
    private static void add(ICommand cmd, String bind) {
        commands.add(cmd);
        commandsBinds.put(bind, cmd.getRoute());
    }

    /**
     * @return Gets the list of all registered commands
     */
    public static List<ICommand> getCommands() {
        return commands;
    }

    /**
     * Gets the command route
     * @param commandBind Bind for that command
     * @return
     */
    public static String getCommandRoute(String commandBind) {
        return commandsBinds.get(commandBind);
    }

    /**
     * @return Gets the commands binds
     */
    public static Map<String, String> getCommandsBinds() {
        return commandsBinds;
    }
}
