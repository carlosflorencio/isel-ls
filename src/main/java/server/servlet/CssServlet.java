package server.servlet;

import app.ImoProject;
import response.implementations.HttpResponseWrapper;
import server.http.HttpStatusCode;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CssServlet extends HttpServlet {

    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.
    private String CssPath;

    public void init() throws ServletException {
        this.CssPath = new File("resources/css").getAbsolutePath(); //css path
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestedCss = request.getPathInfo();

        // Check if file name is actually supplied to the request URI.
        if (requestedCss == null) {
            // Do your thing if the Css is not supplied to the request URI.
            // Throw an exception, or send 404, or show default/warning Css, or just ignore it.
            new HttpResponseWrapper(HttpStatusCode.NotFound); // 404.
            return;
        }

        // Decode the file name (might contain spaces and on) and prepare file object.
        File Css = new File(CssPath, URLDecoder.decode(requestedCss, "UTF-8"));

        // Check if file actually exists in filesystem.
        if (!Css.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
        }

        // Get content type by filename.
        String contentType = getMimeType(Css);

        // Check if file is actually an Css (avoid download of other files by hackers!).
        if (contentType == null || !contentType.startsWith("text")) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
        }

        // Init servlet response.
        response.reset();
        response.setBufferSize(DEFAULT_BUFFER_SIZE);
        response.setContentType(contentType);
        response.setHeader("Content-Length", String.valueOf(Css.length()));

        // Prepare streams.
        BufferedInputStream input = null;
        BufferedOutputStream output = null;

        try {
            // Open streams.
            input = new BufferedInputStream(new FileInputStream(Css), DEFAULT_BUFFER_SIZE);
            output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);

            // Write file contents to response.
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
        } finally {
            // Gently close streams.
            close(output);
            close(input);
        }
    }

    private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                ImoProject.trace("wtf + " + e.getMessage());
            }
        }
    }

    public static String getMimeType(File file) throws IOException {
        Path source = Paths.get(file.getAbsolutePath());
       return Files.probeContentType(source);
    }
}
