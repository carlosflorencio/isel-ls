package commands;

import app.entities.UserEntity;
import database.lib.SqlConnection;
import database.models.DbUsers;
import org.apache.commons.codec.binary.Base64;
import request.IRequest;
import response.IResponse;
import server.http.HttpStatusCode;
import utils.ResolveUrl;
import view.errors.InputErrorHtmlView;
import view.universals.string.UniversalMessageJsonView;
import view.universals.string.UniversalMessageTextView;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public abstract class AuthCommand extends Command {

    protected List<String> necessaryFields;

    public AuthCommand(String route, String method, String desc) {
        super(route, method, desc);
        this.necessaryFields = new LinkedList<String>();
    }
    
    /*
    |--------------------------------------------------------------------------
    | Auth
    |--------------------------------------------------------------------------
    */

    /**
     * Gets the logged user entity, assumes that already exists a valid logged user in that request
     * @param request App request
     * @param response App response
     * @param sqlcon Db connection
     * @return User logged or null
     * @throws SQLException
     */
    protected UserEntity getAuthUser(IRequest request, IResponse response, SqlConnection sqlcon) throws SQLException {
        String username;
        if(request.isHttp()) {
            username = getLoggedUser(request)[0];
        } else {
            username = request.getData("authuser");
        }

        return DbUsers.getUserByUserName(sqlcon, username);
    }


    /**
     * Check if has login from console or from http, if not sends error or ask for login
     * @param request App request
     * @param response App response
     * @param sqlcon Db connection
     * @return True if have a valid login
     * @throws SQLException
     */
    protected boolean haveAuth(IRequest request, IResponse response, SqlConnection sqlcon) throws SQLException {
        if(!request.isHttp()) { //console
            if(!haveConsoleAuth(request, sqlcon))
                return false;
        } else { //http
            if(!haveHttpAuth(request, response, sqlcon))
                return false;
        }

        return true;
    }

    /**
     * Tries to login in a console request
     * @param request Console request
     * @param sqlcon Db connection
     * @return True if has valid login. If false also sets the view
     * @throws SQLException
     */
    protected boolean haveConsoleAuth(IRequest request, SqlConnection sqlcon) throws SQLException {
        necessaryFields.add("authuser");
        necessaryFields.add("authpass");
        if (!hasThisFields(request, "authuser", "authpass")) {
            errorView("You need to authenticate!", ResolveUrl.ofHome());
            return false;
        } else {
            String authUser = request.getData("authuser");
            String authpass = request.getData("authpass");
            if (!DbUsers.getAuth(sqlcon, authUser, authpass)) {
                errorView("You need to authenticate!", ResolveUrl.ofHome());
                return false;
            }
        }
        return true;
    }

    /**
     * Tries to login in a http request, parses the response to ask for auth
     * @param request Http request
     * @param response Http response
     * @param sqlcon Db connection
     * @return True if has a valid auth
     * @throws SQLException
     */
    protected boolean haveHttpAuth(IRequest request, IResponse response, SqlConnection sqlcon) throws SQLException {
        if(haveAuthorization(request)) {
            String[] data = getLoggedUser(request);
            if(data.length != 2 || !DbUsers.getAuth(sqlcon, data[0], data[1])) { //not valid login
                askForAuth(response);
                return false;
            }
        } else {
            askForAuth(response);
            return false;
        }

        return true;
    }

    /*
    |--------------------------------------------------------------------------
    | Http Authorization
    |--------------------------------------------------------------------------
    */
    /**
     * Gets the logged user
     * @param req App request
     * @return Array with username[0] & password[1]
     */
    protected String[] getLoggedUser(IRequest req) {
        String auth = req.headers("Authorization");

        if(auth != null) {
            String[] data = auth.split(" ");
            byte[] decoded = Base64.decodeBase64(data[1]);
            String headerData = new String(decoded);
            data = headerData.split(":");
            return data;
        }
        return null;
    }

    /**
     * Check if the request has the header Authorization
     * @param request App request
     * @return True if it has
     */
    protected boolean haveAuthorization(IRequest request) {
        String auth = request.headers("Authorization");

        return auth != null;
    }

    /**
     * Sends the header to authenticate and sets the response to a NotAuthorized code
     * @param response App response
     */
    protected void askForAuth(IResponse response) {
        response.withHeader("WWW-Authenticate", "Basic realm=\"Auth Needed\"");
        response.setStatusCode(HttpStatusCode.NotAuthorized);
        errorView("You need to authenticate!", ResolveUrl.ofHome());
    }

    /*
	|--------------------------------------------------------------------------
	| Validation
	|--------------------------------------------------------------------------
	 */
    /**
     * Validation of the queryString parameters
     * @param request Request with the queryString sent
     * @return True if all is valid
     */
    protected boolean validateNecessaryFields(IRequest request) {
        for (String necessaryField : this.necessaryFields) {
            if (request.getData(necessaryField) == null)
                return false;
        }
        return true;
    }

    /**
     * Validation of the requested fields for that request
     * @param request Request sent
     * @param fields Fields to be present in the request
     * @return False if some field is not present
     */
    protected boolean hasThisFields(IRequest request, String... fields) {
        for (String field : fields) {
            if (request.getData(field) == null)
                return false;
        }

        return true;
    }

    /*
    |--------------------------------------------------------------------------
    | View
    |--------------------------------------------------------------------------
    */

    /**
     * Sets a custom html view and json/text universal view
     * @param message Error message
     * @param backUrl Url to append to the back button
     */
    protected void errorView(String message, String backUrl) {
        addView("text/html", new InputErrorHtmlView(message, backUrl));
        addView("text/plain", new UniversalMessageTextView(message));
        addView("application/json", new UniversalMessageJsonView(message));
    }
}
