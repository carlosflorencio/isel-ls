package response.implementations;

import org.junit.Test;
import server.http.HttpStatusCode;
import testutils.TestUtils;

import static org.junit.Assert.assertEquals;

public class HttpResponseWrapperTest {

    @Test
    public void testWithHeader() throws Exception {
        HttpResponseWrapper response = TestUtils.getHttpResponseWrapper(HttpStatusCode.Ok, TestUtils.getBody("text/plain", "test"));

        response.withHeader("test", "value");
    }

    @Test
    public void testGetBody() throws Exception {
        HttpResponseWrapper response = TestUtils.getHttpResponseWrapper(HttpStatusCode.Ok, TestUtils.getBody("text/plain", "test"));

        assertEquals("text/plain", response.getBody().getMediaType());
    }

    @Test
    public void testGetStatusCode() throws Exception {
        HttpResponseWrapper response = TestUtils.getHttpResponseWrapper(HttpStatusCode.Ok, TestUtils.getBody("text/plain", "test"));

        assertEquals(HttpStatusCode.Ok, response.getStatusCode());
    }

    @Test
    public void testRedirect() throws Exception {
        HttpResponseWrapper response = TestUtils.getHttpResponseWrapper(HttpStatusCode.Ok, TestUtils.getBody("text/plain", "test"));

        response.redirect("test");
        assertEquals("test", response.getHeader("Location"));
    }

    @Test
    public void testRedirectWithStatusCode() throws Exception {
        HttpResponseWrapper response = TestUtils.getHttpResponseWrapper(HttpStatusCode.Ok, TestUtils.getBody("text/plain", "test"));

        response.redirect("test", HttpStatusCode.BadRequest);
        assertEquals("test", response.getHeader("Location"));
        assertEquals(HttpStatusCode.BadRequest, response.getStatusCode());
    }
}