package database.lib;

import database.DB;
import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SqlQueryTest {
    private static SqlConnection sqlcon;

    @BeforeClass
    public static void initialize() throws SQLException {
        sqlcon = DB.getConnection();
    }

    private void emptyTable(String table) throws SQLException {
        new SqlQuery(sqlcon, "DELETE FROM " + table).executeUpdate();
    }

    @Before
    public void setUp() throws Exception { //clear all tables before each test
        emptyTable("sqlquerytest");
    }

    @Test
    public void testAddString() throws Exception {
        int rows = new SqlQuery(sqlcon, "INSERT INTO sqlquerytest (name) VALUES (?)")
                .addString("carlos")
                .executeUpdate();

        assertEquals(1, rows);

        ResultSet rs = new SqlQuery(sqlcon, "SELECT * FROM sqlquerytest").executeQuery();

        while (rs.next()) {
            assertEquals("carlos", rs.getString("name"));
        }
    }

    @Test
    public void testAddInt() throws Exception {
        int rows = new SqlQuery(sqlcon, "INSERT INTO sqlquerytest (name, age) VALUES (?, ?)")
                .addString("carlos")
                .addInt(10)
                .executeUpdate();

        assertEquals(1, rows);

        ResultSet rs = new SqlQuery(sqlcon, "SELECT * FROM sqlquerytest").executeQuery();

        while (rs.next()) {
            assertEquals("carlos", rs.getString("name"));
            assertEquals(10, rs.getInt("age"));
        }
    }

    @Test
    public void testAddDouble() throws Exception {
        int rows = new SqlQuery(sqlcon, "INSERT INTO sqlquerytest (name, weight) VALUES (?, ?)")
                .addString("carlos")
                .addDouble(65.2)
                .executeUpdate();

        assertEquals(1, rows);

        ResultSet rs = new SqlQuery(sqlcon, "SELECT * FROM sqlquerytest").executeQuery();

        while (rs.next()) {
            assertEquals("carlos", rs.getString("name"));
            assertTrue(65.2 == rs.getDouble("weight"));
        }
    }

    @Test
    public void testAddDateTime() throws Exception {
        DateTime d = new DateTime();
        int rows = new SqlQuery(sqlcon, "INSERT INTO sqlquerytest (name, accepted) VALUES (?, ?)")
                .addString("carlos")
                .addDateTime(d.getMillis())
                .executeUpdate();

        assertEquals(1, rows);

        ResultSet rs = new SqlQuery(sqlcon, "SELECT * FROM sqlquerytest").executeQuery();

        while (rs.next()) {
            assertEquals("carlos", rs.getString("name"));
            DateTime d2 = new DateTime(rs.getTimestamp("accepted").getTime());
            assertTrue(Math.abs(d.getMillis() - d2.getMillis()) < 3L); //sometimes the date retrieved from db is 1ms less
        }
    }

    @Test
    public void testExecute() throws Exception {
        int rows = new SqlQuery(sqlcon, "INSERT INTO sqlquerytest (name, age) VALUES (?, ?)")
                .addString("carlos")
                .addInt(10)
                .executeUpdate();

        assertEquals(1, rows);

        boolean res = new SqlQuery(sqlcon, "SELECT * FROM sqlquerytest").execute();

        assertTrue(res);
    }

    @Test
    public void testReturnLastID() throws Exception {
        int reset = new SqlQuery(sqlcon, "DBCC CHECKIDENT('sqlquerytest', RESEED, 0)") //reset the id counter
                .executeUpdate();

        int row = new SqlQuery(sqlcon, "INSERT INTO sqlquerytest (name, age) VALUES (?, ?)")
                .addString("carlos")
                .addInt(10)
                .executeUpdate();

        int id = new SqlQuery(sqlcon, "INSERT INTO sqlquerytest (name, age) VALUES (?, ?)", true)
                .addString("carlos2")
                .addInt(12)
                .executeUpdate();

        assertEquals(2, id);
    }

    @Test
    public void testCount() throws Exception {
        int rows = new SqlQuery(sqlcon, "INSERT INTO sqlquerytest (name, age) VALUES (?, ?)")
                .addString("carlos")
                .addInt(10)
                .executeUpdate();

        assertEquals(1, rows);

        int count = new SqlQuery(sqlcon, "SELECT COUNT(*) as count FROM sqlquerytest").count("count");

        assertEquals(1, count);
    }

    @AfterClass
    public static void delete() throws SQLException {
        sqlcon.close();
    }
}