package request.implementations;

import request.Request;

import java.util.Map;

public class ConsoleRequest extends Request {


    private String method;
    private String path;
    private String args;

    public ConsoleRequest(String method, String path, String args) {
        this.method = method;
        this.path = path;
        this.args = args;
        this.queryString = this.parseQueryString(this.args);
    }

    @Override
    public String uri() {
        return this.path;
    }

    @Override
    public String method() {
        return this.method;
    }

    @Override
    public String queryStringValue(String name) {
        return this.queryString.get(name);
    }

    @Override
    public Map<String, String> queryString() {
        return this.queryString;
    }

    @Override
    public String acceptType() {
        return this.queryStringValue("accept");
    }

    @Override
    public Map<String, String> binds(String commandRoute) {
        return this.parseBinds(commandRoute, this.uri());
    }

    @Override
    public String headers(String name) {
        return queryStringValue(name);
    }

    @Override
    public String cookie(String name) {
        return null;
    }

    @Override
    public Object attribute(String attribute) {
        return null;
    }

	@Override
	public String getData(String key){
		return queryStringValue(key);
	}

    @Override
    public boolean isHttp() {
        return false;
    }
}
