package response;

import server.http.HttpContent;
import server.http.HttpStatusCode;

import java.io.IOException;

public interface IResponse<E> {

    /**
     * Adds to the response the given header
     * @param name Name of the header
     * @param value Value of the header
     * @return Returns the same response with the new header
     */
    IResponse withHeader(String name, String value);

    /**
     * Modify the original response
     * @param object Original response
     * @return New response
     */
    void send(E originalResponse) throws IOException;

    /**
     * Sets the response status code
     * @param code The new status code of the response
     */
    void setStatusCode(HttpStatusCode code);

    /**
     * Sets the response body to this one
     * @param body The body new content
     */
    void setBody(HttpContent body);

    /**
     * @return Gets the response body
     */
    HttpContent getBody();

    /**
     * @return Get the status code of response, example: 200 OK
     */
    HttpStatusCode getStatusCode();

    /**
     * Redirects the response to another URI
     * @param location New uri to go
     */
    public void redirect(String location);

    /**
     * Redirects the response to another URI with Status code
     * @param location New uri to go
     * @param httpStatusCode Code that go with that response
     */
    public void redirect(String location, HttpStatusCode code);

    /**
     * Gets the response header with that name
     * @param name Name of the header
     * @return Value of the header
     */
    public String getHeader(String name);
}
