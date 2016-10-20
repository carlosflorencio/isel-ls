package commands.delete;

import app.entities.UserEntity;
import database.DB;
import database.lib.SqlConnection;
import database.models.DbProperties;
import exceptions.CommandFailedException;
import request.IRequest;
import response.IResponse;

import java.sql.SQLException;
import java.util.Map;

public class DeletePropertiesByIdCommand extends DeleteCommand {
	
	public DeletePropertiesByIdCommand(String route) {
	super(route, "Delete Rental. Need auth.");
}

	@Override
	public void run(IRequest request, IResponse response) throws CommandFailedException {
		Map<String, String> binds = request.binds(getRoute());

		SqlConnection sqlcon = null;
		try {
			sqlcon = DB.getConnection();

			if(!haveAuth(request, response, sqlcon)) return;

            int id = Integer.parseInt(binds.get("pid"));
            UserEntity user = getAuthUser(request, response, sqlcon);

			if(!DbProperties.isOwnerOfThisProperty(sqlcon, id, user.getUserName())) {
				setUniversalView("You are not the owner of this property.");
				return;
			}

            DbProperties.deletePropertyAndRentals(sqlcon, id);

			setUniversalView("Deleted with success!");
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
	}

}
