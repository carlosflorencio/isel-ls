package utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class PairTest {

    @Test
    public void testGetFirst() throws Exception {
        Pair<Integer, String> p = new Pair<Integer, String>(1, "Carlos");

        assertEquals((Integer)1, p.getFirst());
    }

    @Test
    public void testGetSecond() throws Exception {
        Pair<Integer, String> p = new Pair<Integer, String>(1, "Carlos");

        assertEquals("Carlos", p.getSecond());
    }

    @Test
    public void testEqualsTrue() throws Exception {
        Pair<Integer, String> p = new Pair<Integer, String>(1, "Carlos");
        Pair<Integer, String> p2 = new Pair<Integer, String>(1, "Carlos");

        assertTrue(p.equals(p2));
    }

    @Test
    public void testEqualsFalse() throws Exception {
        Pair<Integer, String> p = new Pair<Integer, String>(2, "Carlos");
        Pair<Integer, String> p2 = new Pair<Integer, String>(1, "Carlos");

        assertFalse(p.equals(p2));
    }
}