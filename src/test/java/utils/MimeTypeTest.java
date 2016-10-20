package utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class MimeTypeTest {

    @Test
    public void testConstructWithoutParameters() {
        MimeType mime = new MimeType("text/html");

        assertEquals("text", mime.getType());
        assertEquals("html", mime.getSubType());
        assertTrue(mime.getParams().isEmpty());
    }

    @Test
    public void testConstructWithParameters() {
        MimeType mime = new MimeType("text/html;q=0.8");

        assertEquals("text", mime.getType());
        assertEquals("html", mime.getSubType());
        assertFalse(mime.getParams().isEmpty());

        assertEquals("0.8", mime.getParam("q"));
    }

    @Test
    public void testEqualsWithTwoValidMimes() throws Exception {
        MimeType mime = new MimeType("text/html;q=0.8");
        MimeType mime2 = new MimeType("text/html;q=0.1");

        assertTrue( mime.equals(mime2) );
    }

    @Test
    public void testEqualsWithTwoInValidMimes() throws Exception {
        MimeType mime = new MimeType("text/plain");
        MimeType mime2 = new MimeType("text/html;q=0.1");

        assertFalse( mime.equals(mime2) );
    }

    @Test
    public void testEqualsWithTwoValidMimesOneIsString() throws Exception {
        MimeType mime = new MimeType("text/plain");

        assertTrue( mime.equals("text/plain") );
    }
}