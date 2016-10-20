package commands.get;

import app.entities.PropertyEntity;
import database.DB;
import database.lib.SqlConnection;
import database.models.DbProperties;
import exceptions.CommandFailedException;
import exceptions.NoSuchDataException;
import request.IRequest;
import response.IResponse;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GetPropertiesByTypeCommand extends GetCommand {
	
	public GetPropertiesByTypeCommand(String path) {
        super(path, "Get properties by type.");
	}

    public void run(IRequest request, IResponse response) throws CommandFailedException, NoSuchDataException {
        Map<String, String> binds = request.binds(getRoute());
		String type = binds.get("type");
		List<PropertyEntity> prop = new ArrayList<PropertyEntity>();

        SqlConnection sqlcon = null;
        try {
            sqlcon = DB.getConnection();
            prop = DbProperties.getPropertiesByType(sqlcon, type);
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

        setUniversalView("properties", prop);
	}
}
