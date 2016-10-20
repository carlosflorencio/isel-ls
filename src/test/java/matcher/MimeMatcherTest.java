package matcher;

import exceptions.NoMimeTypeCompatibleException;
import org.junit.Test;
import server.http.HttpContent;
import testutils.TestUtils;
import utils.MimeType;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class MimeMatcherTest {

    private Map<MimeType, HttpContent> getMap() {
        Map<MimeType, HttpContent> result = new HashMap<MimeType, HttpContent>();

        String[] types = {"text/html", "text/plain", "application/json"};

        for (String type : types) {
            result.put(new MimeType(type), TestUtils.getBody(type, type));
        }

        return result;
    }

    @Test
    public void testValidBestMatch() throws Exception {
        Map<MimeType, HttpContent> map = getMap();

        HttpContent result = MimeMatcher.bestMatch(map, "text/html");
        assertEquals("text/html", result.getMediaType());
    }

    @Test
    public void testInvalidBestMatch() throws Exception {
        Map<MimeType, HttpContent> map = getMap();

        try {
            HttpContent result = MimeMatcher.bestMatch(map, "text/text");
            fail("should throw exception");
        } catch (NoMimeTypeCompatibleException e) {

        }
    }

    @Test
    public void testWithUniversalTypeBestMatch() throws Exception {
        Map<MimeType, HttpContent> map = getMap();

        HttpContent result = MimeMatcher.bestMatch(map, "*/*");
        assertEquals("text/html", result.getMediaType()); //default is text/html
    }

    @Test
    public void testWithBlankTypeBestMatch() throws Exception {
        Map<MimeType, HttpContent> map = getMap();

        HttpContent result = MimeMatcher.bestMatch(map, "");
        assertEquals("text/html", result.getMediaType()); //default is text/html
    }

}