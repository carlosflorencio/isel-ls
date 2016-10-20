package database.models;

import app.entities.RentalEntity;
import database.DB;
import database.lib.SqlConnection;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import testutils.TestUtils;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;

public class DbRentalsTest {
    private static SqlConnection sqlcon;

    @BeforeClass
    public static void initialize() throws SQLException {
        sqlcon = DB.getConnection();
    }

    @Before
    public void setUp() throws Exception {
        TestUtils.emptyTable(sqlcon, "Rentals");
        TestUtils.emptyTable(sqlcon, "Properties");
        TestUtils.emptyTable(sqlcon, "Users");

        TestUtils.resetId(sqlcon, "Users"); //reset id counter
        TestUtils.insertUser(sqlcon, "carlos", "pass", "carlos@gmail.com", "Carlos Florencio");
        TestUtils.insertUser(sqlcon, "oksana", "pass", "oksana@gmail.com", "Oksana Dizdari");

        TestUtils.resetId(sqlcon, "Properties"); //reset id counter
        TestUtils.insertProperty(sqlcon, "apartment", "desc", 100000.2, "Lumiar, Lisboa", "carlos");
        TestUtils.insertProperty(sqlcon, "apartment", "3desc2", 123123, "parque das na��es, Lisboa", "carlos");
        TestUtils.insertProperty(sqlcon, "house", "desc24", 800, "campo grande, Lisboa", "carlos");
        TestUtils.insertProperty(sqlcon, "apartment", "desc4234", 6597, "tavira, Algarve", "oksana");

        TestUtils.insertRental(sqlcon, 1, "oksana", 2014, 36);
        TestUtils.insertRental(sqlcon, 2, "oksana", 2014, 7);
        TestUtils.insertRental(sqlcon, 3, "carlos", 2014, 51);
        TestUtils.insertRental(sqlcon, 4, "carlos", 2014, 4);
        TestUtils.insertRental(sqlcon, 4, "carlos", 2014, 5);
    }

    /*
    |--------------------------------------------------------------------------
    | Begin Tests
    |--------------------------------------------------------------------------
    */

    @Test
    public void testRental() throws Exception {
        long now = Calendar.getInstance().getTimeInMillis();
        RentalEntity rental = DbRentals.rental(sqlcon, 1, "carlos", 2014, 37);

        assertEquals(37, rental.getWeek());
        assertEquals(2014, rental.getYear());
        assertEquals("carlos", rental.getUserName());
        assertEquals(1, rental.getIdProp());
        assertTrue( Math.abs(now - rental.getDataRequest().getMillis()) < 100L ); //100 because the cpu may be slower
        assertNull(rental.getDataAccept());
        assertEquals("pending", rental.getState());
    }

    @Test
    public void testFailedRental() throws Exception {
        try {
            RentalEntity rental = DbRentals.rental(sqlcon, 1, "carlos", 2014, 36);
            fail("should throw an exception");
        } catch (SQLException e) {

        }
    }

    @Test
    public void testGetRentalsByPropId() throws Exception {
        List<RentalEntity> list = DbRentals.getRentalsByPropId(sqlcon, 4);

        assertEquals(2, list.size());
    }

    @Test
    public void testPatchPropertiesRentals() throws Exception {
        RentalEntity rental = DbRentals.getRentalByPropYearWeek(sqlcon, 1, 2014, 36);

        assertEquals("pending", rental.getState());

        assertTrue(DbRentals.updateRentalStateToRented(sqlcon, 1, 2014, 36));

        rental = DbRentals.getRentalByPropYearWeek(sqlcon, 1, 2014, 36);

        assertEquals("rented", rental.getState());
    }

    @Test
    public void testGetRentalByPropYearWeek() throws Exception {
        RentalEntity rental = DbRentals.getRentalByPropYearWeek(sqlcon, 1, 2014, 36);

        assertEquals(36, rental.getWeek());
        assertEquals(2014, rental.getYear());
        assertEquals("oksana", rental.getUserName());
        assertEquals(1, rental.getIdProp());
        assertNull(rental.getDataAccept());
        assertEquals("pending", rental.getState());
    }

    @Test
    public void testDeleteRental() throws Exception {
        List<RentalEntity> list = DbRentals.getRentalsByPropId(sqlcon, 4);

        assertEquals(2, list.size());

        assertTrue( DbRentals.deleteRental(sqlcon, 4, 2014, 4) );

        list = DbRentals.getRentalsByPropId(sqlcon, 4);

        assertEquals(1, list.size());
    }

    @Test
    public void testGetRentalsByUsername() throws Exception {
        List<RentalEntity> list = DbRentals.getRentalsByUsername(sqlcon, "carlos");

        assertEquals(3, list.size());
    }

    @Test
    public void testGetRentalsByPropYear() throws Exception {
        List<RentalEntity> list = DbRentals.getRentalsByPropYear(sqlcon, 4, 2014);

        assertEquals(2, list.size());
    }

    @AfterClass
    public static void delete() throws SQLException {
        sqlcon.close();
    }
}