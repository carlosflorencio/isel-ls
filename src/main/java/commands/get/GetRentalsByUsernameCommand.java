package commands.get;

import app.entities.RentalEntity;
import app.entities.UserEntity;
import database.DB;
import database.lib.SqlConnection;
import database.models.DbRentals;
import database.models.DbUsers;
import exceptions.CommandFailedException;
import exceptions.NoSuchDataException;
import request.IRequest;
import response.IResponse;
import view.rentals.RentalsByUsernameView;
import view.universals.entities.UniversalEntitiesJsonView;
import view.universals.entities.UniversalEntitiesTextView;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class GetRentalsByUsernameCommand extends GetCommand {

	public GetRentalsByUsernameCommand(String route) {
		super(route, "Get Rentals by username.");
	}

	@Override
    public void run(IRequest request, IResponse response) throws CommandFailedException, NoSuchDataException {
        Map<String, String> binds = request.binds(getRoute());
		List<RentalEntity> ren;
		String user = binds.get("username");
        UserEntity u = null;

        SqlConnection sqlcon = null;
        try {
            sqlcon = DB.getConnection();
            ren = DbRentals.getRentalsByUsername(sqlcon, user);
            u = DbUsers.getUserByUserName(sqlcon, user);
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

        if(ren == null || u == null) throw new NoSuchDataException();

        this.addView("text/html", new RentalsByUsernameView(u, ren));
        this.addView("text/plain", new UniversalEntitiesTextView(ren));
        this.addView("application/json", new UniversalEntitiesJsonView("rentals", ren));
	}

}
