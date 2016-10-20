package request.implementations;

import org.junit.Test;
import testutils.TestUtils;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class HttpRequestTest {

    @Test
    public void testUri() throws Exception {
        HttpRequest request = TestUtils.getHttpRequest("GET", "/test", "test=ola", "text/plain");

        assertEquals("/test", request.uri());
    }

    @Test
    public void testMethod() throws Exception {
        HttpRequest request = TestUtils.getHttpRequest("GET", "/test", "test=ola", "text/plain");

        assertEquals("GET", request.method());
    }

    @Test
    public void testQueryStringValue() throws Exception {
        HttpRequest request = TestUtils.getHttpRequest("GET", "/test", "test=ola", "text/plain");

        assertEquals("ola", request.queryStringValue("test"));
    }

    @Test
    public void testQueryString() throws Exception {
        HttpRequest request = TestUtils.getHttpRequest("GET", "/test", "test=ola", "text/plain");

        Map<String, String> queryStr = request.queryString();
        assertEquals(1, queryStr.size());
        assertEquals("ola", queryStr.get("test"));
    }

    @Test
    public void testAcceptType() throws Exception {
        HttpRequest request = TestUtils.getHttpRequest("GET", "/test", "test=ola", "text/plain");

        assertEquals("text/plain", request.acceptType());
    }

    @Test
    public void testBinds() throws Exception {
        HttpRequest request = TestUtils.getHttpRequest("GET", "/test/5/ola", "test=ola", "text/plain");
        String route = "/test/{id}/ola";

        Map<String, String> binds = request.binds(route);
        assertEquals(1, binds.size());
        assertEquals("5", binds.get("id"));
    }

    @Test
    public void testHeaders() throws Exception {
        HttpRequest request = TestUtils.getHttpRequest("GET", "/test/5/ola", "test=ola", "text/plain");

        assertNull(request.headers("oi"));
    }

    @Test
    public void testCookie() throws Exception {
        HttpRequest request = TestUtils.getHttpRequest("GET", "/test/5/ola", "test=ola", "text/plain");

        assertNull(request.cookie("oi"));
    }

    @Test
    public void testAttribute() throws Exception {
        HttpRequest request = TestUtils.getHttpRequest("GET", "/test/5/ola", "test=ola", "text/plain");

        assertNull(request.attribute("oi"));
    }
}