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
import java.util.List;
import java.util.Map;

public class GetPropertiesByLocationCommand extends GetCommand{
	
	public GetPropertiesByLocationCommand(String route) {
		super(route, "Get properties by location.");
	}

    public void run(IRequest request, IResponse response) throws CommandFailedException, NoSuchDataException {
        Map<String, String> binds = request.binds(getRoute());
		String loc = binds.get("location");
		loc=loc.replace("+", " ");
		loc=loc.replace("|", " ");
		List<PropertyEntity> prop;

        SqlConnection sqlcon = null;
        try {
            sqlcon = DB.getConnection();
            prop = DbProperties.getPropertiesByLocal(sqlcon, loc);

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
