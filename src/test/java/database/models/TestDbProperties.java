package database.models;

import app.entities.PropertyEntity;
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

public class TestDbProperties {
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
        TestUtils.insertUser(sqlcon, "carlos", "pass", "carlos@gmail.com", "Carlos Florencio");
        TestUtils.insertUser(sqlcon, "oksana", "pass", "oksana@gmail.com", "Oksana Dizdari");

        TestUtils.resetId(sqlcon, "Properties"); //reset id counter
        TestUtils.insertProperty(sqlcon, "apartment", "desc", 100000.2, "Lumiar, Lisboa", "carlos");
        TestUtils.insertProperty(sqlcon, "apartment", "3desc2", 123123, "parque das nações, Lisboa", "carlos");
        TestUtils.insertProperty(sqlcon, "house", "desc24", 800, "campo grande, Lisboa", "carlos");
        TestUtils.insertProperty(sqlcon, "apartment", "desc4234", 6597, "tavira, Algarve", "oksana");
        TestUtils.insertProperty(sqlcon, "house", "desc32", 45886.15, "viseu, Porto", "oksana");
        TestUtils.insertProperty(sqlcon, "apartment", "desc324", 8978, "gaia, Porto", "carlos");
    }

    /*
    |--------------------------------------------------------------------------
    | Begin Tests
    |--------------------------------------------------------------------------
    */

    @Test
    public void testGetProperties() throws SQLException {
        List<PropertyEntity> props = DbProperties.getProperties(sqlcon);

        assertEquals(6, props.size()); //size should be right

        assertEquals("apartment", props.get(0).getType()); //we only need to test one, if right all others shoud be too
        assertEquals("desc324", props.get(0).getDesc());
        assertTrue(8978 == props.get(0).getPrice());
        assertEquals("gaia, Porto", props.get(0).getLocalization());
        assertEquals("carlos", props.get(0).getOwnerName());
    }

    @Test
    public void testGetPropertiesByLocal() throws SQLException {
        List<PropertyEntity> props = DbProperties.getPropertiesByLocal(sqlcon, "porto");

        assertEquals(2, props.size());

        assertEquals("apartment", props.get(0).getType());
        assertEquals("desc324", props.get(0).getDesc());
        assertTrue(8978 == props.get(0).getPrice());
        assertEquals("gaia, Porto", props.get(0).getLocalization());
        assertEquals("carlos", props.get(0).getOwnerName());

        assertEquals("house", props.get(1).getType());
        assertEquals("desc32", props.get(1).getDesc());
        assertTrue(45886.15 == props.get(1).getPrice());
        assertEquals("viseu, Porto", props.get(1).getLocalization());
        assertEquals("oksana", props.get(1).getOwnerName());
    }

    @Test
    public void testGetPropertiesByOwner() throws SQLException {
        List<PropertyEntity> props = DbProperties.getPropertiesByOwner(sqlcon, "oksana");

        assertEquals(2, props.size());

        assertEquals("house", props.get(0).getType());
        assertEquals("desc32", props.get(0).getDesc());
        assertTrue(45886.15 == props.get(0).getPrice());
        assertEquals("viseu, Porto", props.get(0).getLocalization());
        assertEquals("oksana", props.get(0).getOwnerName());

        assertEquals("apartment", props.get(1).getType());
        assertEquals("desc4234", props.get(1).getDesc());
        assertTrue(6597 == props.get(1).getPrice());
        assertEquals("tavira, Algarve", props.get(1).getLocalization());
        assertEquals("oksana", props.get(1).getOwnerName());
    }

    @Test
    public void testGetPropertiesByType() throws SQLException {
        List<PropertyEntity> props = DbProperties.getPropertiesByType(sqlcon, "house");

        assertEquals(2, props.size());

        assertEquals("house", props.get(0).getType());
        assertEquals("desc32", props.get(0).getDesc());
        assertTrue(45886.15 == props.get(0).getPrice());
        assertEquals("viseu, Porto", props.get(0).getLocalization());
        assertEquals("oksana", props.get(0).getOwnerName());

        assertEquals("house", props.get(1).getType());
        assertEquals("desc24", props.get(1).getDesc());
        assertTrue(800 == props.get(1).getPrice());
        assertEquals("campo grande, Lisboa", props.get(1).getLocalization());
        assertEquals("carlos", props.get(1).getOwnerName());
    }

    @Test
    public void testGetPropertiesById() throws SQLException {
        PropertyEntity prop = DbProperties.getPropertiesById(sqlcon, 1);

        assertEquals("apartment", prop.getType());
        assertEquals("desc", prop.getDesc());
        assertTrue(100000.2 == prop.getPrice());
        assertEquals("Lumiar, Lisboa", prop.getLocalization());
        assertEquals("carlos", prop.getOwnerName());
    }

    @Test
    public void testEmptyGetPropertiesById() throws SQLException {
        PropertyEntity prop = DbProperties.getPropertiesById(sqlcon, 150);

        assertNull(prop);
    }

    @Test
    public void testinsertProperty() throws SQLException {
        PropertyEntity prop = new PropertyEntity("house", "desc", 20123.2, "lisboa", "carlos");
        int id = DbProperties.insertProperty(sqlcon, prop);

        assertEquals(7, id);

        PropertyEntity prop2 = DbProperties.getPropertiesById(sqlcon, 7);
        assertEquals(prop.getType(), prop2.getType());
        assertEquals(prop.getDesc(), prop2.getDesc());
        assertTrue(prop.getPrice() == prop2.getPrice());
        assertEquals(prop.getLocalization(), prop2.getLocalization());
        assertEquals(prop.getOwnerName(), prop2.getOwnerName());
    }

    @Test
    public void testIsOwnerOfThatProperty() throws SQLException {
        assertTrue( DbProperties.isOwnerOfThisProperty(sqlcon, 1, "carlos") );
        assertFalse(DbProperties.isOwnerOfThisProperty(sqlcon, 2, "oksana"));
    }

    @Test
    public void testEmptyGetPropertiesByLocal() throws SQLException {
        List<PropertyEntity> props = DbProperties.getPropertiesByLocal(sqlcon, "nao existe");

        assertEquals(0, props.size());
    }

    @AfterClass
    public static void delete() throws SQLException {
        sqlcon.close();
    }
}
