package app.entities;

import org.junit.Test;
import utils.Pair;

import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PropertyEntityTest {

    private PropertyEntity getEntity(int id, String type, String desc, double price, String local, String owner) {
        return new PropertyEntity(id, type, desc, price, local, owner);
    }

    @Test
    public void testGetId() throws Exception {
        PropertyEntity entity = getEntity(1, "casa", "descricao", 14.5, "almada", "carlos");

        assertEquals(1, entity.getId());
    }

    @Test
    public void testGetType() throws Exception {
        PropertyEntity entity = getEntity(1, "casa", "descricao", 14.5, "almada", "carlos");

        assertEquals("casa", entity.getType());
    }

    @Test
    public void testGetDesc() throws Exception {
        PropertyEntity entity = getEntity(1, "casa", "descricao", 14.5, "almada", "carlos");

        assertEquals("descricao", entity.getDesc());
    }

    @Test
    public void testGetPrice() throws Exception {
        PropertyEntity entity = getEntity(1, "casa", "descricao", 14.5, "almada", "carlos");

        assertTrue(14.5 == entity.getPrice());
    }

    @Test
    public void testGetLocalization() throws Exception {
        PropertyEntity entity = getEntity(1, "casa", "descricao", 14.5, "almada", "carlos");

        assertEquals("almada", entity.getLocalization());
    }

    @Test
    public void testGetOwnerName() throws Exception {
        PropertyEntity entity = getEntity(1, "casa", "descricao", 14.5, "almada", "carlos");

        assertEquals("carlos", entity.getOwnerName());
    }

    @Test
    public void testSetId() throws Exception {
        PropertyEntity p = new PropertyEntity("casa", "desc", 12.1, "local", "owner");

        assertEquals(0, p.getId() );

        p.setId(2);

        assertEquals(2, p.getId());
    }

    @Test
    public void testResponse() throws Exception {
        PropertyEntity entity = getEntity(1, "casa", "descricao", 14.5, "almada", "carlos");

        List<Pair<String, Object>> pairs = entity.response();
        Iterator<Pair<String, Object>> itr = pairs.iterator();

        assertEquals(new Pair<String, Object>("id", 1), itr.next());
        assertEquals(new Pair<String, Object>("type", "casa"), itr.next());
        assertEquals(new Pair<String, Object>("description", "descricao"), itr.next());
        assertEquals(new Pair<String, Object>("price", 14.5), itr.next());
        assertEquals(new Pair<String, Object>("localization", "almada"), itr.next());
        assertEquals(new Pair<String, Object>("owner", "carlos"), itr.next());
    }
}