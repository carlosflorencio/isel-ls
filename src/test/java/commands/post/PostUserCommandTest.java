package commands.post;

import app.RegisterCommands;
import commands.ICommand;
import commands.container.TreeContainer;
import database.DB;
import database.lib.SqlConnection;
import exceptions.CommandFailedException;
import exceptions.NoSuchDataException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import request.IRequest;
import response.IResponse;
import server.http.HttpContent;
import testutils.TestUtils;
import utils.MimeType;

import java.io.StringWriter;
import java.sql.SQLException;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class PostUserCommandTest {
	
	private static SqlConnection sqlcon = null;
	
	@BeforeClass
    public static void initialize() throws SQLException {
        sqlcon = DB.getConnection();
    }
	
    @Before
    public void setUp() throws SQLException { //Prepare the database Data
    	
    	TestUtils.emptyTable(sqlcon, "Rentals");
        TestUtils.emptyTable(sqlcon, "Properties");
        TestUtils.emptyTable(sqlcon, "Users");
    }
    
    private Map<MimeType, HttpContent> runCommandWithCorrectValuesAndGetViews() throws CommandFailedException, NoSuchDataException {
        ICommand cmd = new PostUserCommand("/users");

        IRequest request = TestUtils.getConsoleRequest("POST", "/users", "username=carlos&password=pass2&email=carlos@gmail.com&fullname=Carlos+Florencio&authuser=oksana&authpass=pass");
        IResponse response = TestUtils.getConsoleResponseWrapper(null);

        RegisterCommands.register(new TreeContainer()); //necessary to register routes used in command views

        cmd.run(request, response);

        return cmd.getViews();
    }
    
    private Map<MimeType, HttpContent> runCommandWithWrongValuesAndGetViews() throws CommandFailedException, NoSuchDataException {
        ICommand cmd = new PostUserCommand("/users");

        IRequest request = TestUtils.getConsoleRequest("POST", "/users", "username=carlos&password=pass2&email=carlos@gmail.com&fullname=Carlos+Florencio&authuser=carlos&authpass=pass2");
        IResponse response = TestUtils.getConsoleResponseWrapper(null);

        RegisterCommands.register(new TreeContainer()); //necessary to register routes used in command views

        cmd.run(request, response);

        return cmd.getViews();
    }
    
    @Test
    public void testRunWithValidValue() throws Exception{
    	TestUtils.resetId(sqlcon, "Users"); //reset id counter
    	TestUtils.insertUser(sqlcon, "oksana", "pass", "oksana@gmail.com", "Oksana Dizdari");
    	
    	Map<MimeType, HttpContent> views = runCommandWithCorrectValuesAndGetViews();

        assertTrue( TestUtils.validViews(views) );
        
        //Text view should have info about a new user
        StringWriter sw = new StringWriter();
        views.get(new MimeType("text/plain")).writeTo(sw);
        String result = sw.toString();
        assertTrue( result.contains("username = carlos"));
    }
    
    @Test
    public void testRunWithInvalidValue() throws Exception{
    	TestUtils.resetId(sqlcon, "Users"); //reset id counter
    	TestUtils.insertUser(sqlcon, "carlos", "pass2", "carlos@gmail.com", "Carlos Florencio");
    	
    	Map<MimeType, HttpContent> views = runCommandWithWrongValuesAndGetViews();

        assertTrue( TestUtils.validViews(views) );
        
        //Text view should have "That username/email already exists"
        StringWriter sw = new StringWriter();
        views.get(new MimeType("text/plain")).writeTo(sw);
        String result = sw.toString();
        assertTrue( result.contains("That username/email already exists!"));
    }

}
