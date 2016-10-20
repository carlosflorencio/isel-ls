package database.models;

import app.entities.UserEntity;
import database.DB;
import database.lib.SqlConnection;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import testutils.TestUtils;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;


public class TestDbUsers {
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
    }

    /*
    |--------------------------------------------------------------------------
    | Begin Tests
    |--------------------------------------------------------------------------
    */

    @Test
    public void testGetUsers() throws SQLException {
        List<UserEntity> users = DbUsers.getUsers(sqlcon);

        assertEquals(2, users.size()); //size should be right

        assertEquals("oksana", users.get(0).getUserName()); //we only need to test one, if right all others shoud be too
        assertEquals("pass", users.get(0).getPassW());
        assertEquals("oksana@gmail.com", users.get(0).getEmail());
        assertEquals("Oksana Dizdari", users.get(0).getName());
    }

    @Test
    public void testGetUserByUsername() throws SQLException {
        UserEntity user = DbUsers.getUserByUserName(sqlcon, "oksana");

        assertEquals("oksana", user.getUserName()); //we only need to test one, if right all others shoud be too
        assertEquals("pass", user.getPassW());
        assertEquals("oksana@gmail.com", user.getEmail());
        assertEquals("Oksana Dizdari", user.getName());
    }

    @Test
    public void testGetAuth() throws SQLException {
        assertTrue( DbUsers.getAuth(sqlcon, "carlos", "pass") );
        assertFalse(DbUsers.getAuth(sqlcon, "oksana", "oi"));
    }

    @Test
    public void testInsertUser() throws SQLException {
        int id = DbUsers.insertUser(sqlcon, new UserEntity("paulo", "pass", "asd@asd.com", "Paulo"));

        assertEquals(3, id);

        UserEntity user = DbUsers.getUserByUserName(sqlcon, "paulo");

        assertEquals("paulo", user.getUserName());
        assertEquals("pass", user.getPassW());
        assertEquals("asd@asd.com", user.getEmail());
        assertEquals("Paulo", user.getName());
    }

    @Test
    public void testEmptyGetUserByUsername() throws SQLException {
       UserEntity user = DbUsers.getUserByUserName(sqlcon, "nao existe");

        assertNull(user);
    }

    @AfterClass
    public static void delete() throws SQLException {
        sqlcon.close();
    }
}

