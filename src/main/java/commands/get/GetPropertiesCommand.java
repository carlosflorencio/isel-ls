package commands.get;

import app.entities.PropertyEntity;
import database.DB;
import database.lib.SqlConnection;
import database.models.DbProperties;
import exceptions.CommandFailedException;
import exceptions.NoSuchDataException;
import request.IRequest;
import response.IResponse;
import view.properties.PropertiesView;
import view.universals.entities.UniversalEntitiesJsonView;
import view.universals.entities.UniversalEntitiesTextView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetPropertiesCommand extends GetCommand {

	public GetPropertiesCommand(String path) {
        super(path, "List all properties.");
	}

	@Override
    public void run(IRequest request, IResponse response) throws CommandFailedException, NoSuchDataException {
		List<PropertyEntity> prop = new ArrayList<PropertyEntity>();

        SqlConnection sqlcon = null;
        try {
            sqlcon = DB.getConnection();
            prop = DbProperties.getProperties(sqlcon);
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

        this.addView("text/html", new PropertiesView(prop));
        this.addView("text/plain", new UniversalEntitiesTextView(prop));
        this.addView("application/json", new UniversalEntitiesJsonView("properties", prop));
	}

}
