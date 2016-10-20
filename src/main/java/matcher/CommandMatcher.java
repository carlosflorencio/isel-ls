package matcher;

import app.ImoProject;
import commands.ICommand;
import commands.container.IContainer;
import exceptions.CommandFailedException;
import exceptions.CommandNotFoundException;
import exceptions.NoMimeTypeCompatibleException;
import exceptions.NoSuchDataException;
import request.IRequest;
import response.IResponse;
import response.implementations.ConsoleResponseWrapper;
import response.implementations.HttpResponseWrapper;
import server.error.ErrorController;
import server.http.HttpContent;
import server.http.HttpStatusCode;
import utils.MimeType;
import view.home.HomeView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

public class CommandMatcher {

    private final IContainer container;

    public CommandMatcher(IContainer container) {
        this.container = container;
    }

    /*
    |--------------------------------------------------------------------------
    | Http Request
    |--------------------------------------------------------------------------
    */
    /**
     * Find the right command and put the result in the response body
     * @param request Http Request
     * @param originalResponse Where to write the response
     */
    public void execute(IRequest request, HttpServletResponse originalResponse) throws IOException {
        try {
            IResponse<HttpServletResponse> response = new HttpResponseWrapper(HttpStatusCode.Ok);

            if(request.uri().equals("/")) {
                response.setBody(new HomeView());
                response.send(originalResponse);
                return;
            }

            ICommand command = this.container.find(request.method(), request.uri());

            command.run(request, response);
            Map<MimeType, HttpContent> commandResult = command.getViews();

            HttpContent body = MimeMatcher.bestMatch(commandResult, request.acceptType());

            response.setBody(body);
            response.send(originalResponse);

        } catch (CommandNotFoundException | NoSuchDataException e) {
            HttpContent errorView = new ErrorController(HttpStatusCode.NotFound, request.acceptType()).getView();
            new HttpResponseWrapper(HttpStatusCode.NotFound, errorView)
                    .send(originalResponse);
        } catch (IOException | CommandFailedException | NoMimeTypeCompatibleException e) {
            HttpContent errorView = new ErrorController(HttpStatusCode.InternalServerError, request.acceptType()).getView();
            new HttpResponseWrapper(HttpStatusCode.InternalServerError, errorView)
                    .send(originalResponse);
        }
    }

    /*
    |--------------------------------------------------------------------------
    | Console Request
    |--------------------------------------------------------------------------
    */
    /**
     * Find the right command and put the result in the response body
     * @param request Console Request
     * @param originalResponse Where to write the response
     */
    public void execute(IRequest request, StringWriter originalResponse) {
        IResponse<StringWriter> badResponse = new ConsoleResponseWrapper(new HomeView());

        try {
            ICommand command = this.container.find(request.method(), request.uri());

            IResponse<StringWriter> response = new ConsoleResponseWrapper(new HomeView());

            command.run(request, response);
            Map<MimeType, HttpContent> commandResult = command.getViews();

            HttpContent body = MimeMatcher.bestMatch(commandResult, request.acceptType());

            response.setBody(body);
            response.send(originalResponse);

        } catch (CommandNotFoundException e) {
            ImoProject.trace("That command was not found!");
        } catch (IOException e) {
            ImoProject.trace("Something failed with IO operations.");
        } catch (CommandFailedException e) {
            ImoProject.trace("Something failed executing that command.");
        } catch (NoMimeTypeCompatibleException e) {
            ImoProject.trace("That command does not have a view of that mimeType associated.");
        } catch (NoSuchDataException e) {
            ImoProject.trace("Not found the right data, maybe it does not exist yet!");
        }
    }

}
