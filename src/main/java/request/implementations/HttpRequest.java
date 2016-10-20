package request.implementations;


import request.Request;
import server.http.FormUrlEncoded;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

public class HttpRequest extends Request {

    protected HttpServletRequest original;
    Map<String, String> form = null;

    public HttpRequest(HttpServletRequest servletResponse) throws IOException {
        super();
        this.original = servletResponse;

        this.queryString = this.parseQueryString(this.original.getQueryString());

        if(FormUrlEncoded.canRetriveFrom(original)) {
            form = FormUrlEncoded.retrieveFrom(original);
        }
    }

    @Override
    public String uri() {
        return original.getRequestURI();
    }

    @Override
    public String method() {
        return original.getMethod();
    }

    @Override
    public String queryStringValue(String name) {
        if(this.queryString().isEmpty()) return null;

        return queryString().get(name);
    }

    @Override
    public Map<String, String> queryString() {
        return this.queryString;
    }

    @Override
    public String acceptType() {
        return this.headers("Accept");
    }

    @Override
    public Map<String, String> binds(String commandRoute) {
        return this.parseBinds(commandRoute, this.uri());
    }

    @Override
    public String headers(String name) {
        return this.original.getHeader(name);
    }

    @Override
    public String cookie(String name) {
        Cookie[] cookies = this.original.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    @Override
    public Object attribute(String attribute) {
        return this.original.getAttribute(attribute);
    }

	@Override
	public String getData(String key){
        if(form != null && form.containsKey(key))
            return form.get(key);

		return queryStringValue(key);
	}

    @Override
    public boolean isHttp() {
        return true;
    }
}
