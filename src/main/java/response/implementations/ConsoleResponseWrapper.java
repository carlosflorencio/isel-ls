package response.implementations;

import response.IResponse;
import server.http.HttpContent;
import server.http.HttpStatusCode;

import java.io.IOException;
import java.io.StringWriter;

public class ConsoleResponseWrapper implements IResponse<StringWriter> {

    protected HttpContent body;

    public ConsoleResponseWrapper(HttpContent body) {
        this.body = body;
    }

    @Override
    public IResponse withHeader(String name, String value) {
        return this;
    }

    @Override
    public void send(StringWriter originalResponse) throws IOException {
        body.writeTo(originalResponse);
    }

    @Override
    public void setStatusCode(HttpStatusCode code) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setBody(HttpContent body) {
        this.body = body;
    }

    @Override
    public HttpContent getBody() {
        return this.body;
    }

    @Override
    public HttpStatusCode getStatusCode() {
        return null;
    }

    @Override
    public void redirect(String location) {

    }

    @Override
    public void redirect(String location, HttpStatusCode code) {

    }

    @Override
    public String getHeader(String name) {
        return null;
    }
}
