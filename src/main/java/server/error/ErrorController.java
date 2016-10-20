package server.error;

import exceptions.NoMimeTypeCompatibleException;
import matcher.MimeMatcher;
import server.http.HttpContent;
import server.http.HttpStatusCode;
import utils.MimeType;
import view.errors.InternalServerErrorHtmlView;
import view.errors.NotFoundHtmlView;
import view.universals.string.UniversalMessageJsonView;
import view.universals.string.UniversalMessageTextView;

import java.util.HashMap;
import java.util.Map;

public class ErrorController {

    protected Map<HttpStatusCode, Map<MimeType, HttpContent>> errorsMap = new HashMap<HttpStatusCode, Map<MimeType, HttpContent>>();
    private HttpStatusCode code;
    private String acceptType;

    /**
     * Has all the errors views
     * @param code Status Code
     */
    public ErrorController(HttpStatusCode code, String acceptType) {
        this.code = code;
        this.acceptType = acceptType;
        this.addViews();
    }

    /**
     * Gets the view for that mimeType
     * @return View
     */
    public HttpContent getView() {
        Map<MimeType, HttpContent> map = errorsMap.get(code);

        if(map == null) return null;

        try {
            return MimeMatcher.bestMatch(map, this.acceptType);
        } catch (NoMimeTypeCompatibleException e) {
            return null;
        }
    }

    /**
     * Adds the views to the map
     */
    protected void addViews() {
        Map<MimeType, HttpContent> notFoundViews = new HashMap<MimeType, HttpContent>();
        notFoundViews.put(new MimeType("text/plain"), new UniversalMessageTextView("Not found!"));
        notFoundViews.put(new MimeType("application/json"), new UniversalMessageJsonView("Not found!"));
        notFoundViews.put(new MimeType("text/html"), new NotFoundHtmlView());

        Map<MimeType, HttpContent> internalErrorViews = new HashMap<MimeType, HttpContent>();
        internalErrorViews.put(new MimeType("text/plain"), new UniversalMessageTextView("Server error!"));
        internalErrorViews.put(new MimeType("application/json"), new UniversalMessageJsonView("Server error!"));
        internalErrorViews.put(new MimeType("text/html"), new InternalServerErrorHtmlView());

        errorsMap.put(HttpStatusCode.NotFound, notFoundViews);
        errorsMap.put(HttpStatusCode.InternalServerError, internalErrorViews);
    }

}
