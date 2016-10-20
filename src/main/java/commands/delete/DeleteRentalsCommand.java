package commands.delete;

import app.entities.UserEntity;
import database.DB;
import database.lib.SqlConnection;
import database.models.DbProperties;
import database.models.DbRentals;
import exceptions.CommandFailedException;
import request.IRequest;
import response.IResponse;

import java.sql.SQLException;
import java.util.Map;

public class DeleteRentalsCommand extends DeleteCommand{

	public DeleteRentalsCommand(String route) {
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
            int y = Integer.parseInt(binds.get("year"));
            int w = Integer.parseInt(binds.get("cw"));

            UserEntity user = getAuthUser(request, response, sqlcon);

            if(!DbProperties.isOwnerOfThisProperty(sqlcon, id, user.getUserName())) {
                setUniversalView("You are not the owner of this property.");
                return;
            }

            if(!DbRentals.deleteRental(sqlcon, id, y, w)) {
                setUniversalView("No rental request in that date or already rented.");
                return;
            }

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
