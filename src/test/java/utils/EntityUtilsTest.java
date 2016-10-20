package utils;

import app.entities.UserEntity;
import app.entities.body.IEntityResponsable;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EntityUtilsTest {

    @Test
    public void testPlainTextEntity() throws Exception {
        UserEntity user = new UserEntity("carlos", "pass", "carlos@gmail.com", "Carlos Florencio");

        String result = EntityUtils.plainTextEntity(user);

        assertTrue( result.contains("carlos") );
        assertTrue( result.contains("carlos@gmail.com") );
        assertTrue( result.contains("Carlos Florencio") );
    }

    @Test
    public void testGetColumns() throws Exception {
        UserEntity user = new UserEntity("carlos", "pass", "carlos@gmail.com", "Carlos Florencio");
        List<IEntityResponsable> list = new LinkedList<IEntityResponsable>();
        list.add(user);

        List<String> result = EntityUtils.getColumns(list);

        assertEquals("username", result.get(0));
        assertEquals("email", result.get(1));
        assertEquals("name", result.get(2));
        assertTrue(result.size() == 3);
    }

    @Test
    public void testGetRows() throws Exception {
        UserEntity user = new UserEntity("carlos", "pass", "carlos@gmail.com", "Carlos Florencio");
        UserEntity user2 = new UserEntity("carlos2", "pass2", "carlos2@gmail.com", "Carlos2 Florencio");
        List<IEntityResponsable> list = new LinkedList<IEntityResponsable>();
        list.add(user);
        list.add(user2);

        List<List<Object>> result = EntityUtils.getRows(list);

        //row 1
        assertEquals("carlos", result.get(0).get(0));
        assertEquals("carlos@gmail.com", result.get(0).get(1));
        assertEquals("Carlos Florencio", result.get(0).get(2));

        //row 2
        assertEquals("carlos2", result.get(1).get(0));
        assertEquals("carlos2@gmail.com", result.get(1).get(1));
        assertEquals("Carlos2 Florencio", result.get(1).get(2));

        assertTrue(result.size() == 2 && result.get(0).size() == 3);
    }
}