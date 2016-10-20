package request;

import java.util.Map;

public interface IRequest {

    /**
     * @return The request URI example: /users
     */
    String uri();

    /**
     * @return The request method, example: GET, POST
     */
    String method();

    /**
     * Get the value of queryString request name
     * @param name Name of the value in queryString
     * @return Value of that name
     */
    String queryStringValue(String name);

    /**
     * @return Get the request queryString mapped by key => value
     */
    Map<String, String> queryString();

    /**
     * @return Request acceptType, ex: text/html
     */
    String acceptType();

    /**
     * @return Get the binds from the request by key => value
     */
    Map<String, String> binds(String commandRoute);

    /**
     * Gets the value for the provided header
     * @param name The header
     * @return The value
     */
    String headers(String name);

    /**
     * Gets cookie by name
     * @param name Name of the cookie
     * @return Cookie value
     */
    String cookie(String name);

    /**
     * Gets the value of the provided attribute
     * @param attribute The attribute
     * @return The attribute value
     */
    Object attribute(String attribute);

    /**
     * Find values from form if exists, then queryString if not
     * @param key Key to search
     * @return Data from key
     */
    String getData(String key);

    /**
     * @return True if is http request
     */
    boolean isHttp();

}
