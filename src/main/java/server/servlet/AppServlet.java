package server.servlet;

import app.ImoProject;
import matcher.CommandMatcher;
import request.implementations.HttpRequest;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AppServlet extends HttpServlet {

    /*
    |--------------------------------------------------------------------------
    | Methods
    |--------------------------------------------------------------------------
    */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        parse(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        parse(req, resp);
    }

    private void parse(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println( String.format("Received %s request for %s", req.getMethod(), req.getRequestURI()));

        CommandMatcher cm = new CommandMatcher(ImoProject.getContainer());

        cm.execute(new HttpRequest(req), resp);
    }

}
