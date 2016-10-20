package response.implementations;

import org.junit.Test;
import testutils.TestUtils;

import java.io.StringWriter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ConsoleResponseWrapperTest {

    @Test
    public void testWithHeader() throws Exception {
        ConsoleResponseWrapper response = TestUtils.getConsoleResponseWrapper(TestUtils.getBody("text/plain", "test"));

        response.withHeader("test", "value"); //dont do anything, we just make sure it doens throw exception
    }

    @Test
    public void testSend() throws Exception {
        ConsoleResponseWrapper response = TestUtils.getConsoleResponseWrapper(TestUtils.getBody("text/plain", "test"));
        StringWriter sw = new StringWriter();

        response.send(sw);

        assertEquals("test", sw.toString());
    }

    @Test
    public void testGetBody() throws Exception {
        ConsoleResponseWrapper response = TestUtils.getConsoleResponseWrapper(TestUtils.getBody("text/plain", "test"));

        assertEquals("text/plain", response.getBody().getMediaType());
    }

    @Test
    public void testGetStatusCode() throws Exception {
        ConsoleResponseWrapper response = TestUtils.getConsoleResponseWrapper(TestUtils.getBody("text/plain", "test"));

        assertNull(response.getStatusCode());
    }

    @Test
    public void testRedirect() throws Exception {
        ConsoleResponseWrapper response = TestUtils.getConsoleResponseWrapper(TestUtils.getBody("text/plain", "test"));

        response.redirect("test"); //dont do anything, we just make sure it doens throw exception
    }
}