package commands.get;

import app.entities.PropertyEntity;
import database.DB;
import database.lib.SqlConnection;
import database.models.DbProperties;
import exceptions.CommandFailedException;
import exceptions.NoSuchDataException;
import request.IRequest;
import response.IResponse;
import view.properties.SinglePropertyHtmlView;
import view.universals.entity.UniversalEntityJsonView;
import view.universals.entity.UniversalEntityTextView;

import java.sql.SQLException;
import java.util.Map;

public class GetPropertiesByIdCommand extends GetCommand {
	
	public GetPropertiesByIdCommand(String path) {
		super(path, "Get properties by id.");
	}

	public void run(IRequest request, IResponse response) throws CommandFailedException, NoSuchDataException {
        Map<String, String> binds = request.binds(getRoute());

		int id = Integer.parseInt(binds.get("pid"));
		PropertyEntity prop = null;

        SqlConnection sqlcon = null;

        try {
            sqlcon = DB.getConnection();
            prop = DbProperties.getPropertiesById(sqlcon, id);
        } catch (Exception e) {
            throw new CommandFailedException(e);
        } finally {
            if (sqlcon != null)
                try {
                    sqlcon.close();
                } catch (SQLException e) {
                    throw new CommandFailedException(e);
                }
        }

        if(prop == null) throw new NoSuchDataException();

        addView("text/html", new SinglePropertyHtmlView(prop));
        addView("text/plain", new UniversalEntityTextView(prop));
        addView("application/json", new UniversalEntityJsonView(prop));
	}

	
}
