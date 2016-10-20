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

public class PostRentalCommandTest {
	
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
        ICommand cmd = new PostRentalCommand("/properties/{pid}/rentals");

        IRequest request = TestUtils.getConsoleRequest("POST", "/properties/1/rentals", "year=2014&cw=3&authuser=carlos&authpass=pass");
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
    	TestUtils.insertUser(sqlcon, "carlos", "pass", "carlos@gmail.com", "Carlos Florencio");
    	TestUtils.insertProperty(sqlcon, "vivenda", "descricao1", 12000.00, "loc1", "oksana");
    	
    	Map<MimeType, HttpContent> views = runCommandAndGetViews();

        assertTrue( TestUtils.validViews(views) );
        
        //Text view should have info about a changed rental
        StringWriter sw = new StringWriter();
        views.get(new MimeType("text/plain")).writeTo(sw);
        String result = sw.toString();
       
        assertTrue( result.contains("state = pending"));
        assertTrue( result.contains("idProp = 1"));
        assertTrue( result.contains("userName = carlos"));
    }
    
    @Test
    public void testRunWithoutValidValue() throws Exception{
    	TestUtils.resetId(sqlcon, "Users"); //reset id counter
    	TestUtils.resetId(sqlcon, "Properties");//reset id counter
    	TestUtils.insertUser(sqlcon, "oksana", "pass", "oksana@gmail.com", "Oksana Dizdari");
    	TestUtils.insertUser(sqlcon, "carlos", "pass", "carlos@gmail.com", "Carlos Florencio");
    	TestUtils.insertUser(sqlcon, "bia", "pass", "bia@gmail.com", "Beatriz Neto");
    	TestUtils.insertProperty(sqlcon, "vivenda", "descricao1", 12000.00, "loc1", "oksana");
    	TestUtils.insertRental(sqlcon, 1, "bia", 2014, 3);
    	
    	Map<MimeType, HttpContent> views = runCommandAndGetViews();

        assertTrue( TestUtils.validViews(views) );
        
        //Text view should have "There is already a rent request for that date."
        StringWriter sw = new StringWriter();
        views.get(new MimeType("text/plain")).writeTo(sw);
        String result = sw.toString();
        assertTrue( result.contains("There is already a rent request for that date."));
    }

}
