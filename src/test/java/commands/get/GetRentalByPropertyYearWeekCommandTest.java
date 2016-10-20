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

import static org.junit.Assert.assertTrue;

public class GetRentalByPropertyYearWeekCommandTest {
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
    	
        ICommand cmd = new GetRentalByPropertyYearWeekCommand("/properties/{pid}/rentals/{year}/{cw}");

        IRequest request = TestUtils.getConsoleRequest("GET", "/properties/2/rentals/2013/20", "");
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
        TestUtils.insertUser(sqlcon, "bia", "pass", "beatriz@gmail.com", "Beatriz Neto");
        TestUtils.insertProperty(sqlcon, "vivenda", "descricao1", 12000.00, "loc1", "oksana");
        TestUtils.insertProperty(sqlcon, "vivenda", "descricao2", 11000.50, "loc2", "carlos");
        TestUtils.insertProperty(sqlcon, "apartamento", "descricao3", 1000.00, "loc2", "carlos");
        TestUtils.insertRental(sqlcon, 1, "bia", 2014, 1);
        TestUtils.insertRental(sqlcon, 2, "bia", 2013, 20);
        TestUtils.insertRental(sqlcon, 3, "oksana", 2014, 13);
        TestUtils.insertRental(sqlcon, 1, "carlos", 2014, 3);

        Map<MimeType, HttpContent> views = runCommandAndGetViews();

        assertTrue( TestUtils.validViews(views) );

        //Text view should have only a rental with idProp = 2
        StringWriter sw = new StringWriter();
        views.get(new MimeType("text/plain")).writeTo(sw);
        String result = sw.toString();
        
        assertTrue( result.contains("idProp = 2"));
        assertTrue( result.contains("year = 2013"));
        assertTrue( result.contains("week = 20"));
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
