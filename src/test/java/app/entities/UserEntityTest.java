package app.entities;

import org.junit.Test;
import utils.Pair;

import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserEntityTest {

    @Test
    public void testGetUserName() throws Exception {
        UserEntity entity = new UserEntity(1, "carlos", "pass", "a@a.com", "carlos florencio");

        assertEquals("carlos", entity.getUserName());
    }

    @Test
    public void testGetPassW() throws Exception {
        UserEntity entity = new UserEntity(1, "carlos", "pass", "a@a.com", "carlos florencio");

        assertEquals("pass", entity.getPassW());
    }

    @Test
    public void testGetEmail() throws Exception {
        UserEntity entity = new UserEntity(1, "carlos", "pass", "a@a.com", "carlos florencio");

        assertEquals("a@a.com", entity.getEmail());
    }

    @Test
    public void testGetName() throws Exception {
        UserEntity entity = new UserEntity(1, "carlos", "pass", "a@a.com", "carlos florencio");

        assertEquals("carlos florencio", entity.getName());
    }

    @Test
    public void testGetId() throws Exception {
        UserEntity entity = new UserEntity(1, "carlos", "pass", "a@a.com", "carlos florencio");

        assertEquals(1, entity.getId());
    }

    @Test
    public void testResponse() throws Exception {
        UserEntity entity = new UserEntity(1, "carlos", "pass", "a@a.com", "carlos florencio");

        List<Pair<String, Object>> pairs = entity.response();
        Iterator<Pair<String, Object>> itr = pairs.iterator();

        assertEquals(new Pair<String, Object>("username", "carlos"), itr.next());
        assertEquals(new Pair<String, Object>("email", "a@a.com"), itr.next());
        assertEquals(new Pair<String, Object>("name", "carlos florencio"), itr.next());
    }
}