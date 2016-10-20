package commands.get;

import app.RegisterCommands;
import commands.ICommand;
import commands.container.TreeContainer;
import database.DB;
import database.lib.SqlConnection;
import exceptions.CommandFailedException;
import exceptions.NoSuchDataException;
import org.junit.*;
import org.junit.rules.ExpectedException;
import request.IRequest;
import response.IResponse;
import server.http.HttpContent;
import testutils.TestUtils;
import utils.MimeType;

import java.io.StringWriter;
import java.sql.SQLException;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GetUserByUsernameCommandTest {
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
    
    private Map<MimeType, HttpContent> runCommandAndGetViews() throws CommandFailedException, NoSuchDataException {
        ICommand cmd = new GetUserByUsernameCommand("/users/{username}");

        IRequest request = TestUtils.getConsoleRequest("GET", "/users/oksana", "");
        IResponse response = TestUtils.getConsoleResponseWrapper(null);

        RegisterCommands.register(new TreeContainer()); //necessary to register routes used in command views

        cmd.run(request, response);

        return cmd.getViews();
    }
    
    @Test
    public void testRunWithValidValues() throws Exception{
        TestUtils.resetId(sqlcon, "Users"); //reset id counter
        TestUtils.insertUser(sqlcon, "carlos", "pass", "carlos@gmail.com", "Carlos Florencio");
        TestUtils.insertUser(sqlcon, "oksana", "pass", "oksana@gmail.com", "Oksana Dizdari");

        Map<MimeType, HttpContent> views = runCommandAndGetViews();

        assertTrue( TestUtils.validViews(views) );

        //Text view should have at only a user oksana
        StringWriter sw = new StringWriter();
        views.get(new MimeType("text/plain")).writeTo(sw);
        String result = sw.toString();

        assertTrue( result.contains("oksana"));
        assertFalse( result.contains("carlos"));
    }
    
    @Rule
	public ExpectedException exception = ExpectedException.none();
    
    @Test
    public void testRunWithoutValues() throws Exception {
    	exception.expect(NoSuchDataException.class);
        Map<MimeType, HttpContent> views = runCommandAndGetViews();

    }
    
    @AfterClass
    public static void stop() throws SQLException {
        sqlcon.close();
    }

}
