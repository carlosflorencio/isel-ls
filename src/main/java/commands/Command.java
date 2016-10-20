package commands;

import app.entities.body.IEntityResponsable;
import server.http.HttpContent;
import utils.MimeType;
import view.universals.entities.UniversalEntitiesHtmlView;
import view.universals.entities.UniversalEntitiesJsonView;
import view.universals.entities.UniversalEntitiesTextView;
import view.universals.entity.UniversalEntityHtmlView;
import view.universals.entity.UniversalEntityJsonView;
import view.universals.entity.UniversalEntityTextView;
import view.universals.string.UniversalMessageHtmlView;
import view.universals.string.UniversalMessageJsonView;
import view.universals.string.UniversalMessageTextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Command implements ICommand {
	
	protected String route;
	protected String method;
    protected String description;
    protected Map<MimeType, HttpContent> views;

    /**
     * Creates a command
     * @param route Command route
     * @param method Command method
     * @param description Command description
     */
    public Command(String route, String method, String description) {
        this.route = route;
        this.method = method;
        this.description = description;
        this.views = new HashMap<MimeType, HttpContent>();
    }

    /*
    |--------------------------------------------------------------------------
    | Add views
    |--------------------------------------------------------------------------
    */

    /**
     * Adds a view to the command
     * @param mime MimeType of the view
     * @param content Body to send to the response
     */
    protected void addView(MimeType mime, HttpContent content) {
        this.views.put(mime, content);
    }

    /**
     * Adds a view to the command
     * @param mime MimeType of the view
     * @param content Body to send to the response
     */
    protected void addView(String mime, HttpContent content) {
        this.views.put(new MimeType(mime), content);
    }

    /*
    |--------------------------------------------------------------------------
    | Set views
    |--------------------------------------------------------------------------
    */

    /**
     * Sets a universal view for the command
     * @param message Data to convert and to send to the response
     */
    protected void setUniversalView(String message) {
        this.addView("text/html", new UniversalMessageHtmlView(message));
        this.addView("text/plain", new UniversalMessageTextView(message));
        this.addView("application/json", new UniversalMessageJsonView(message));
    }

    /**
     * Sets a universal view for the command
     * @param entity Data to convert and to send to the response
     */
    protected void setUniversalView(IEntityResponsable entity) {
        this.addView("text/html", new UniversalEntityHtmlView(entity));
        this.addView("text/plain", new UniversalEntityTextView(entity));
        this.addView("application/json", new UniversalEntityJsonView(entity));
    }

    /**
     * Sets a universal view for the command
     * @param jsonListName Name of the list for the json response type
     * @param entities Data to convert and to send to the response
     */
    protected void setUniversalView(String jsonListName, List<? extends IEntityResponsable> entities) {
        addView("text/html", new UniversalEntitiesHtmlView(entities));
        addView("text/plain", new UniversalEntitiesTextView(entities));
        addView("application/json", new UniversalEntitiesJsonView(jsonListName, entities));
    }

	/*
	|--------------------------------------------------------------------------
	| Getters
	|--------------------------------------------------------------------------
	 */

	public String getMethod() {
		return this.method;
	}

	public String getRoute() {
		return this.route;
	}

    public String getDescription() {
        return this.description;
    }

    public Map<MimeType, HttpContent> getViews() {
        return this.views;
    }

}
