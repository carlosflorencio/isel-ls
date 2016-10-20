package testutils;

import commands.ICommand;
import database.lib.SqlConnection;
import database.lib.SqlQuery;
import exceptions.CommandFailedException;
import org.joda.time.DateTime;
import request.IRequest;
import request.implementations.ConsoleRequest;
import request.implementations.HttpRequest;
import response.IResponse;
import response.implementations.ConsoleResponseWrapper;
import response.implementations.HttpResponseWrapper;
import server.http.HttpContent;
import server.http.HttpStatusCode;
import utils.MimeType;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.security.Principal;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

public class TestUtils {
    
    /*
    |--------------------------------------------------------------------------
    | Command Utils
    |--------------------------------------------------------------------------
    */
    public static ICommand getCommand(final String method, final String route, final Map<MimeType, HttpContent> views) {
        return new ICommand() {
            @Override
            public void run(IRequest request, IResponse response) throws CommandFailedException {}

            @Override
            public String getRoute() {
                return route;
            }

            @Override
            public String getMethod() {
                return method;
            }

            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public Map<MimeType, HttpContent> getViews() {
                return views;
            }

        };
    }

    public static boolean validViews(Map<MimeType, HttpContent> views) {
        if(views.size() != 3) return false;

        if(views.get(new MimeType("text/html")) == null) return false;
        if(views.get(new MimeType("text/plain")) == null) return false;
        if(views.get(new MimeType("application/json")) == null) return false;

        return true;
    }

    /*
    |--------------------------------------------------------------------------
    | Request Utils
    |--------------------------------------------------------------------------
    */
    public static ConsoleRequest getConsoleRequest(String method, String path, String queryString) {
        return new ConsoleRequest(method, path, queryString);
    }

   public static HttpRequest getHttpRequest(String method, String path, String queryString, String acceptType) throws IOException {
        return new HttpRequest( getHttpServletRequest(method, path, queryString, acceptType) );
    }

    public static HttpServletRequest getHttpServletRequest(final String method, final String path, final String queryString, final String acceptType) {
        return new HttpServletRequest() {
            @Override
            public String getAuthType() {
                return null;
            }

            @Override
            public Cookie[] getCookies() {
                return new Cookie[0];
            }

            @Override
            public long getDateHeader(String name) {
                return 0;
            }

            @Override
            public String getHeader(String name) {
                if(name.equals("Accept"))
                    return acceptType;
                return null;
            }

            @Override
            public Enumeration<String> getHeaders(String name) {
                return null;
            }

            @Override
            public Enumeration<String> getHeaderNames() {
                return null;
            }

            @Override
            public int getIntHeader(String name) {
                return 0;
            }

            @Override
            public String getMethod() {
                return method;
            }

            @Override
            public String getPathInfo() {
                return null;
            }

            @Override
            public String getPathTranslated() {
                return null;
            }

            @Override
            public String getContextPath() {
                return null;
            }

            @Override
            public String getQueryString() {
                return queryString;
            }

            @Override
            public String getRemoteUser() {
                return null;
            }

            @Override
            public boolean isUserInRole(String role) {
                return false;
            }

            @Override
            public Principal getUserPrincipal() {
                return null;
            }

            @Override
            public String getRequestedSessionId() {
                return null;
            }

            @Override
            public String getRequestURI() {
                return path;
            }

            @Override
            public StringBuffer getRequestURL() {
                return null;
            }

            @Override
            public String getServletPath() {
                return null;
            }

            @Override
            public HttpSession getSession(boolean create) {
                return null;
            }

            @Override
            public HttpSession getSession() {
                return null;
            }

            @Override
            public boolean isRequestedSessionIdValid() {
                return false;
            }

            @Override
            public boolean isRequestedSessionIdFromCookie() {
                return false;
            }

            @Override
            public boolean isRequestedSessionIdFromURL() {
                return false;
            }

            @Override
            public boolean isRequestedSessionIdFromUrl() {
                return false;
            }

            @Override
            public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
                return false;
            }

            @Override
            public void login(String username, String password) throws ServletException {

            }

            @Override
            public void logout() throws ServletException {

            }

            @Override
            public Collection<Part> getParts() throws IOException, ServletException {
                return null;
            }

            @Override
            public Part getPart(String name) throws IOException, ServletException {
                return null;
            }

            @Override
            public Object getAttribute(String name) {
                return null;
            }

            @Override
            public Enumeration<String> getAttributeNames() {
                return null;
            }

            @Override
            public String getCharacterEncoding() {
                return null;
            }

            @Override
            public void setCharacterEncoding(String env) throws UnsupportedEncodingException {

            }

            @Override
            public int getContentLength() {
                return 0;
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public ServletInputStream getInputStream() throws IOException {
                return null;
            }

            @Override
            public String getParameter(String name) {
                return null;
            }

            @Override
            public Enumeration<String> getParameterNames() {
                return null;
            }

            @Override
            public String[] getParameterValues(String name) {
                return new String[0];
            }

            @Override
            public Map<String, String[]> getParameterMap() {
                return null;
            }

            @Override
            public String getProtocol() {
                return null;
            }

            @Override
            public String getScheme() {
                return null;
            }

            @Override
            public String getServerName() {
                return null;
            }

            @Override
            public int getServerPort() {
                return 0;
            }

            @Override
            public BufferedReader getReader() throws IOException {
                return null;
            }

            @Override
            public String getRemoteAddr() {
                return null;
            }

            @Override
            public String getRemoteHost() {
                return null;
            }

            @Override
            public void setAttribute(String name, Object o) {

            }

            @Override
            public void removeAttribute(String name) {

            }

            @Override
            public Locale getLocale() {
                return null;
            }

            @Override
            public Enumeration<Locale> getLocales() {
                return null;
            }

            @Override
            public boolean isSecure() {
                return false;
            }

            @Override
            public RequestDispatcher getRequestDispatcher(String path) {
                return null;
            }

            @Override
            public String getRealPath(String path) {
                return null;
            }

            @Override
            public int getRemotePort() {
                return 0;
            }

            @Override
            public String getLocalName() {
                return null;
            }

            @Override
            public String getLocalAddr() {
                return null;
            }

            @Override
            public int getLocalPort() {
                return 0;
            }

            @Override
            public ServletContext getServletContext() {
                return null;
            }

            @Override
            public AsyncContext startAsync() throws IllegalStateException {
                return null;
            }

            @Override
            public AsyncContext startAsync(ServletRequest servletRequest,
                                           ServletResponse servletResponse) throws IllegalStateException {
                return null;
            }

            @Override
            public boolean isAsyncStarted() {
                return false;
            }

            @Override
            public boolean isAsyncSupported() {
                return false;
            }

            @Override
            public AsyncContext getAsyncContext() {
                return null;
            }

            @Override
            public DispatcherType getDispatcherType() {
                return null;
            }
        };
    }

    /*
    |--------------------------------------------------------------------------
    | Response Utils
    |--------------------------------------------------------------------------
    */
    public static ConsoleResponseWrapper getConsoleResponseWrapper(HttpContent body) {
        return new ConsoleResponseWrapper(body);
    }

    public static HttpResponseWrapper getHttpResponseWrapper(HttpStatusCode code, HttpContent body) {
        return new HttpResponseWrapper(code, body);
    }

    /*
    |--------------------------------------------------------------------------
    | Response body Utils
    |--------------------------------------------------------------------------
    */
    public static HttpContent getBody(final String type, final String content) {
        return new HttpContent() {
            @Override
            public String getMediaType() {
                return type;
            }

            @Override
            public void writeTo(Writer w) throws IOException {
                w.write(content);
            }
        };
    }

    /*
    |--------------------------------------------------------------------------
    | Db Utils
    |--------------------------------------------------------------------------
    */
    public static void emptyTable(SqlConnection sqlcon, String table){
        try {
			new SqlQuery(sqlcon, "DELETE FROM " + table).executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
    }

    public static int insertProperty(SqlConnection sqlcon, String type, String desc, double price, String location, String owner) throws SQLException {
        return new SqlQuery(sqlcon, "INSERT INTO Properties (imoType, imoDesc, price, location, ownername) VALUES (?,?,?,?,?)")
                .addString(type)
                .addString(desc)
                .addDouble(price)
                .addString(location)
                .addString(owner)
                .executeUpdate();
    }

    public static int insertUser(SqlConnection sqlcon, String username, String password, String email, String name) throws SQLException {
        return new SqlQuery(sqlcon, "INSERT INTO Users VALUES (?,?,?,?)")
                .addString(username)
                .addString(password)
                .addString(email)
                .addString(name)
                .executeUpdate();
    }

    public static int insertRental(SqlConnection con, int idProperty, String username, int year, int week) throws SQLException {
        return new SqlQuery(con, "INSERT INTO Rentals (idProp,username, yearRen, weekRen, stateRen, dateRequest) VALUES (?, ?, ?, ?, ?, ?)")
                .addInt(idProperty)
                .addString(username)
                .addInt(year)
                .addInt(week)
                .addString("pending")
                .addDateTime(new DateTime().getMillis())
                .executeUpdate();
    }

    public static void resetId(SqlConnection sqlcon, String table) throws SQLException {
        new SqlQuery(sqlcon, "DBCC CHECKIDENT(?, RESEED, 0)").addString(table).executeUpdate();
    }
}
