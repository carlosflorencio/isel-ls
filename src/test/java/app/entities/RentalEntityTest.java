package app.entities;

import org.joda.time.DateTime;
import org.junit.Test;
import utils.Pair;

import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class RentalEntityTest {

    private RentalEntity getEntity() {
        long req = System.currentTimeMillis();
        long accept = req + 5;
        return new RentalEntity(1, "carlos", 2014, 2, "rented", new DateTime(req), new DateTime(accept));
    }

    @Test
    public void testGetIdProp() throws Exception {
        RentalEntity entity = getEntity();

        assertEquals(1, entity.getIdProp());
    }

    @Test
    public void testGetUserName() throws Exception {
        RentalEntity entity = getEntity();

        assertEquals("carlos", entity.getUserName());
    }

    @Test
    public void testGetYear() throws Exception {
        RentalEntity entity = getEntity();

        assertEquals(2014, entity.getYear());
    }

    @Test
    public void testGetWeek() throws Exception {
        RentalEntity entity = getEntity();

        assertEquals(2, entity.getWeek());
    }

    @Test
    public void testGetState() throws Exception {
        RentalEntity entity = getEntity();

        assertEquals("rented", entity.getState());
    }

    @Test
    public void testGetDataRequest() throws Exception {
        long now = System.currentTimeMillis();
        RentalEntity entity = new RentalEntity(1, "carlos", 2014, 2, "rented", new DateTime(now), new DateTime(now));

        assertEquals(new DateTime(now), entity.getDataRequest());
    }

    @Test
    public void testGetDataAccept() throws Exception {
        long now = System.currentTimeMillis();
        RentalEntity entity = new RentalEntity(1, "carlos", 2014, 2, "rented", new DateTime(now), new DateTime(now));

        assertEquals(new DateTime(now), entity.getDataAccept());
    }

    @Test
    public void testResponse() throws Exception {
        long now = System.currentTimeMillis();
        RentalEntity entity = new RentalEntity(1, "carlos", 2014, 2, "rented", new DateTime(now), new DateTime(now));

        List<Pair<String, Object>> pairs = entity.response();
        Iterator<Pair<String, Object>> itr = pairs.iterator();

        assertEquals(new Pair<String, Object>("idProp", 1), itr.next());
        assertEquals(new Pair<String, Object>("userName", "carlos"), itr.next());
        assertEquals(new Pair<String, Object>("year", 2014), itr.next());
        assertEquals(new Pair<String, Object>("week", 2), itr.next());
        assertEquals(new Pair<String, Object>("state", "rented"), itr.next());
        assertEquals(new Pair<String, Object>("dateRequest", new DateTime(now)), itr.next());
        assertEquals(new Pair<String, Object>("dateAccept", new DateTime(now)), itr.next());
    }
}