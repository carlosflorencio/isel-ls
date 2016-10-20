package commands;

import exceptions.CommandFailedException;
import exceptions.NoSuchDataException;
import request.IRequest;
import response.IResponse;
import server.http.HttpContent;
import utils.MimeType;

import java.util.Map;

public interface ICommand {

    /**
     * Run the command
     * @param request Request
     * @param response Response
     * @return Mapped Views by mimeType
     * @throws CommandFailedException
     */
	public void run(IRequest request, IResponse response) throws CommandFailedException, NoSuchDataException;

    /**
     * @return Gets the command route
     */
	public String getRoute();

    /**
     * @return Gets the command method
     */
	public String getMethod();

    /**
     * @return Gets the command description
     */
    public String getDescription();

    /**
     * @return Gets the command associated views for different mimeTypes
     */
    public Map<MimeType, HttpContent> getViews();
}
