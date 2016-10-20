package utils;

import app.console.commands.ListenConsoleCommand;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ResolveUrlTest {

    @Test
    public void testOfHome() throws Exception {
        String aurl = ListenConsoleCommand.getServerUrl();

        assertEquals(aurl, ResolveUrl.ofHome());
    }

    @Test
    public void testOfWithRouteAndParameters() throws Exception {
        String aurl = ListenConsoleCommand.getServerUrl();
        Map<String, String> map = new HashMap<>();
        map.put("test", "/test/{id}/{test}");

        ResolveUrl.setCommandBinds(map);

        assertEquals(aurl + "/test/1/ola", ResolveUrl.of("test", 1, "ola"));
    }

    @Test
    public void testOfWithOnlyRoute() throws Exception {
        String aurl = ListenConsoleCommand.getServerUrl();
        Map<String, String> map = new HashMap<>();
        map.put("test", "/test/oi");

        ResolveUrl.setCommandBinds(map);

        assertEquals(aurl + "/test/oi", ResolveUrl.of("test"));
    }
}