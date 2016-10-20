package request.implementations;

import org.junit.Test;
import testutils.TestUtils;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ConsoleRequestTest {

    @Test
    public void testUri() throws Exception {
        ConsoleRequest request = TestUtils.getConsoleRequest("GET", "/test", "test=ola");

        assertEquals("/test", request.uri());
    }

    @Test
    public void testMethod() throws Exception {
        ConsoleRequest request = TestUtils.getConsoleRequest("GET", "/test", "test=ola");

        assertEquals("GET", request.method());
    }

    @Test
    public void testQueryStringValue() throws Exception {
        ConsoleRequest request = TestUtils.getConsoleRequest("GET", "/test", "test=ola");

        assertEquals("ola", request.queryStringValue("test"));
    }

    @Test
    public void testQueryString() throws Exception {
        ConsoleRequest request = TestUtils.getConsoleRequest("GET", "/test", "test=ola");

        Map<String, String> queryStr = request.queryString();
        assertEquals(1, queryStr.size());
        assertEquals("ola", queryStr.get("test"));
    }

    @Test
    public void testNullAcceptType() throws Exception {
        ConsoleRequest request = TestUtils.getConsoleRequest("GET", "/test", "test=ola");

        assertNull(request.acceptType());
    }

    @Test
    public void testValidAcceptType() throws Exception {
        ConsoleRequest request = TestUtils.getConsoleRequest("GET", "/test", "test=ola&accept=text/plain");

        assertEquals("text/plain", request.acceptType());
    }

    @Test
    public void testBinds() throws Exception {
        ConsoleRequest request = TestUtils.getConsoleRequest("GET", "/test/5/ola", "test=ola");
        String route = "/test/{id}/ola";

        Map<String, String> binds = request.binds(route);
        assertEquals(1, binds.size());
        assertEquals("5", binds.get("id"));
    }

    @Test
    public void testHeaders() throws Exception {
        ConsoleRequest request = TestUtils.getConsoleRequest("GET", "/test/5/ola", "test=ola&oi=2");

        assertEquals("2", request.headers("oi"));
    }

    @Test
    public void testCookie() throws Exception {
        ConsoleRequest request = TestUtils.getConsoleRequest("GET", "/test/5/ola", "test=ola&oi=2");

        assertNull(request.cookie("test"));
    }

    @Test
    public void testAttribute() throws Exception {
        ConsoleRequest request = TestUtils.getConsoleRequest("GET", "/test/5/ola", "test=ola&oi=2");

        assertNull(request.attribute("test"));
    }
}