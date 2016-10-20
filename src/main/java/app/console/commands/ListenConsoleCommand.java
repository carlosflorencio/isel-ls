package app.console.commands;

import app.ImoProject;
import app.console.ConsoleCommand;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import server.servlet.AppServlet;
import server.servlet.CssServlet;
import server.servlet.ImageServlet;
import server.servlet.JsServlet;

import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListenConsoleCommand extends ConsoleCommand {

    private static int listen_port = 8080;
    private static String default_url = "http://localhost";

    public ListenConsoleCommand() {
        super(Pattern.compile("LISTEN \\/( port=(\\d+))?", Pattern.CASE_INSENSITIVE), "Start HTTP Server");
    }

    @Override
    public void run(String cmd) {
        parsePort(cmd);

        try {
            //turnOffLog();

            Server server = new Server(listen_port);
            ServletHandler handler = new ServletHandler();
            server.setHandler(handler);
            handler.addServletWithMapping(CssServlet.class, "/resources/css/*");
            handler.addServletWithMapping(JsServlet.class, "/resources/js/*");
            handler.addServletWithMapping(ImageServlet.class, "/resources/images/*");
            handler.addServletWithMapping(AppServlet.class, "/*");
            server.start();
            System.out.println("Server is started");

            System.in.read();
            server.stop();
            System.out.println("Server is stopped, bye");
        } catch (IOException e) {
            ImoProject.trace("Error in IO: " + e);
        } catch (Exception e) {
            System.out.println("Error with the http server.");
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void turnOffLog() {
        Properties p = new Properties();
        p.setProperty("org.eclipse.jetty.LEVEL", "WARN");
        org.eclipse.jetty.util.log.StdErrLog.setProperties(p);
    }

    private void parsePort(String cmd) {
        Matcher m = regex.matcher(cmd);
        String port = null;
        while(m.find()) {
            port = m.group(2);
        }
        	
        if(port != null) {
        	listen_port = Integer.parseInt(port);
        }
        
    }
    
    public static String getServerUrl() {
    	return default_url + ":" + listen_port;
    }
}
