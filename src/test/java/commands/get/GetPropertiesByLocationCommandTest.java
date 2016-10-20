package commands.get;

import app.RegisterCommands;
import commands.ICommand;
import commands.container.TreeContainer;
import database.DB;
import database.lib.SqlConnection;
import exceptions.CommandFailedException;
import exceptions.NoSuchDataException;
import org.junit.AfterClass;
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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GetPropertiesByLocationCommandTest {
	
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
        ICommand cmd = new GetPropertiesByLocationCommand("/properties/location/{location}");

        IRequest request = TestUtils.getConsoleRequest("GET", "/properties/location/loc2", "");
        IResponse response = TestUtils.getConsoleResponseWrapper(null);

        RegisterCommands.register(new TreeContainer()); //necessary to register routes used in command views

        cmd.run(request, response);

        return cmd.getViews();
    }
    
    @Test
    public void testRunWithValidValues() throws Exception{
    	TestUtils.resetId(sqlcon, "Users"); //reset id counter
        TestUtils.resetId(sqlcon, "Properties");//reset id counter
        TestUtils.insertUser(sqlcon, "carlos", "pass", "carlos@gmail.com", "Carlos Florencio");
        TestUtils.insertUser(sqlcon, "oksana", "pass", "oksana@gmail.com", "Oksana Dizdari");
        TestUtils.insertProperty(sqlcon, "vivenda", "descricao1", 12000.00, "loc1", "oksana");
        TestUtils.insertProperty(sqlcon, "vivenda", "descricao2", 11000.50, "loc2", "carlos");
        TestUtils.insertProperty(sqlcon, "apartamento", "descricao3", 1000.00, "loc2", "carlos");

        Map<MimeType, HttpContent> views = runCommandAndGetViews();

        assertTrue( TestUtils.validViews(views) );

        //Text view should have only a property with id = 1 and owner = oksana
        StringWriter sw = new StringWriter();
        views.get(new MimeType("text/plain")).writeTo(sw);
        String result = sw.toString();
        
        assertFalse( result.contains("id = 1"));
        assertTrue( result.contains("id = 2"));
        assertTrue( result.contains("id = 3"));
        assertTrue( result.contains("owner = carlos"));
        assertTrue( result.contains("localization = loc2"));
    }
    
    
    @Test
    public void testRunWithoutValues() throws Exception {
        Map<MimeType, HttpContent> views = runCommandAndGetViews();
        
        assertTrue( TestUtils.validViews(views) );
        
        StringWriter sw = new StringWriter();
        views.get(new MimeType("application/json")).writeTo(sw);
        String result = sw.toString();
        assertTrue( result.equals("{\"properties\":[]}"));
    }
    
    
    @Test
    public void testRunWithoutCorrectValues() throws Exception {
    	TestUtils.resetId(sqlcon, "Users"); //reset id counter
        TestUtils.resetId(sqlcon, "Properties");//reset id counter
        TestUtils.insertUser(sqlcon, "carlos", "pass", "carlos@gmail.com", "Carlos Florencio");
        TestUtils.insertUser(sqlcon, "oksana", "pass", "oksana@gmail.com", "Oksana Dizdari");
        TestUtils.insertProperty(sqlcon, "vivenda", "descricao1", 12000.00, "loc1", "oksana");
        
    	Map<MimeType, HttpContent> views = runCommandAndGetViews();

        assertTrue( TestUtils.validViews(views) );

        StringWriter sw = new StringWriter();
        views.get(new MimeType("application/json")).writeTo(sw);
        String result = sw.toString();

        assertTrue( result.equals("{\"properties\":[]}")); //empty json response
    }
    
    @AfterClass
    public static void stop() throws SQLException {
        sqlcon.close();
    }


}
