package server.http;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class FormUrlEncoded {

    public static boolean canRetriveFrom(HttpServletRequest t) {
        return t.getHeader("Content-Type") != null && t.getHeader("Content-Type").equals("application/x-www-form-urlencoded");
    }
    
    // TODO assumes Content-Length present and UTF-8 charset
    public static Map<String,String> retrieveFrom(HttpServletRequest req) throws IOException{
        Map<String, String> map = new HashMap<String,String>();
        byte[] bytes = new byte[req.getContentLength()];
        req.getInputStream().read(bytes);
        String content = new String(bytes);
        String[] pairs = content.split("&");
        for(String pair : pairs) {
            String[] kvp = pair.split("=");
            if(kvp.length != 2) continue;
            map.put(URLDecoder.decode(kvp[0], "UTF-8"),
                    URLDecoder.decode(kvp[1], "UTF-8"));
        }
        return map;
    }
    
}
