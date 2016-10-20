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

public class PostPropertyCommandTest {
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
        ICommand cmd = new PostPropertyCommand("/properties");

        IRequest request = TestUtils.getConsoleRequest("POST", "/properties", "type=vivenda&description=Muito+bom+estado&price=20000.00&location=loc&authuser=oksana&authpass=pass");
        IResponse response = TestUtils.getConsoleResponseWrapper(null);

        RegisterCommands.register(new TreeContainer()); //necessary to register routes used in command views

        cmd.run(request, response);

        return cmd.getViews();
    }
    
    
    @Test
    public void testRunWithValidValue() throws Exception{
    	TestUtils.resetId(sqlcon, "Users"); //reset id counter
    	TestUtils.resetId(sqlcon, "Properties");//reset id counter
    	TestUtils.insertUser(sqlcon, "oksana", "pass", "oksana@gmail.com", "Oksana Dizdari");
    	
    	Map<MimeType, HttpContent> views = runCommandAndGetViews();

        assertTrue( TestUtils.validViews(views) );
        
        //Text view should have info about a new property
        StringWriter sw = new StringWriter();
        views.get(new MimeType("text/plain")).writeTo(sw);
        String result = sw.toString();
       
        assertTrue( result.contains("owner = oksana"));
    }

}
