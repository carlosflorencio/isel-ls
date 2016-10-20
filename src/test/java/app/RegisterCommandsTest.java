package app;

import commands.ICommand;
import commands.container.IContainer;
import commands.container.TreeContainer;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class RegisterCommandsTest {

    private String replaceBindsWith(String route, String replace) {
        return route.replaceAll("\\{[A-Za-z0-1_-]+\\}", replace);
    }

    @Test
    public void testRegisterIfAllCommandsAreFound() throws Exception {
        IContainer tree = new TreeContainer();

        RegisterCommands.register(tree);

        assertEquals(RegisterCommands.getCommands().size(), tree.size());

        List<ICommand> commands = RegisterCommands.getCommands();

        for (ICommand command : commands) {
            String realRoute = replaceBindsWith(command.getRoute(), "1");
            System.out.println(command.getClass());
            assertEquals(command.getRoute(), tree.find(command.getMethod(), realRoute).getRoute());
        }
    }

    @Test
    public void testParse() {
        String route = "/properties/{pid}/rentals/{year}/{cw}";

        String result = replaceBindsWith(route, "1");

        assertEquals("/properties/1/rentals/1/1", result);
    }
}