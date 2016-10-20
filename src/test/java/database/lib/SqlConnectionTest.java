package database.lib;

import database.DB;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class SqlConnectionTest {
    private static SqlConnection con;

    @BeforeClass
    public static void initialize() throws SQLException {
        con = DB.getConnection();
    }

    @Test
    public void testBeginTransaction() throws Exception {
        assertTrue(con.connection().getAutoCommit());

        con.beginTransaction();

        assertFalse(con.connection().getAutoCommit());
    }

    @Test
    public void testEndTransaction() throws Exception {
        con.beginTransaction();

        assertFalse(con.connection().getAutoCommit());

        con.endTransaction();

        assertTrue(con.connection().getAutoCommit());
    }

    @Test
    public void testAddStatement() throws Exception {
        SqlConnection con2 = DB.getConnection();
        con2.addStatement(con.connection().prepareStatement("SELECT username FROM Users LIMIT 1"));

        assertEquals(1, con2.getStatements().size());
        con2.close();
    }

    @Test
    public void testRemoveStatement() throws Exception {
        SqlConnection con2 = DB.getConnection();

        PreparedStatement ps = con2.connection().prepareStatement("SELECT username FROM Users LIMIT 1");

        con2.addStatement(ps);

        assertEquals(1, con2.getStatements().size());

        con2.removeStatement(ps);

        assertEquals(0, con2.getStatements().size());
        con2.close();
    }

    @AfterClass
    public static void delete() throws SQLException {
        con.close();
    }
}