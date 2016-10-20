package response.implementations;

import response.IResponse;
import server.http.HttpContent;
import server.http.HttpStatusCode;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class HttpResponseWrapper implements IResponse<HttpServletResponse> {

    protected HttpStatusCode status;
    protected HttpContent body;

    protected Charset _charset = Charset.forName("UTF-8");
    protected Map<String, String> headers = new HashMap<String, String>();

    public HttpResponseWrapper() {
    }

    public HttpResponseWrapper(HttpStatusCode status) {
        this(status, null);
    }

    public HttpResponseWrapper(HttpStatusCode status, HttpContent body) {
        this.status = status;
        this.body = body;
    }

    public HttpResponseWrapper withHeader(String name, String value) {
        this.headers.put(name, value);
        return this;
    }

    @Override
    public void send(HttpServletResponse originalResponse) throws IOException {
        for(Map.Entry<String, String> entry : headers.entrySet()){
            originalResponse.addHeader(entry.getKey(), entry.getValue());
        }

        if(body == null) {
            sendWithoutBody(originalResponse);
        }else {
            sendWithBufferedBody(originalResponse);
        }
    }

    private void sendWithoutBody(HttpServletResponse resp) throws IOException {
        resp.setStatus(status.valueOf());
    }
    
    private void sendWithBufferedBody(HttpServletResponse resp) throws IOException {
    	
    	ByteArrayOutputStream _os = new ByteArrayOutputStream();
        try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(_os, _charset))){
        	body.writeTo(writer);
        }
        byte[] bytes = _os.toByteArray();
        resp.setStatus(status.valueOf());
        String ctype = String.format("%s;charset=%s", body.getMediaType(), _charset.name());
        resp.setContentType(ctype);
        resp.setContentLength(bytes.length);
        OutputStream ros = resp.getOutputStream();
        ros.write(bytes);
        ros.close();        
    }

    public void setStatusCode(HttpStatusCode code) {
        this.status = code;
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
        return this.status;
    }

    @Override
    public void redirect(String location) {
        setStatusCode(HttpStatusCode.SeeOther);
        this.withHeader("Location", location);
    }

    @Override
    public void redirect(String location, HttpStatusCode code) {
        this.withHeader("Location", location);
        this.setStatusCode(code);
    }

    @Override
    public String getHeader(String name) {
        return headers.get(name);
    }
}
