package commands.get;

import app.entities.RentalEntity;
import database.DB;
import database.lib.SqlConnection;
import database.models.DbRentals;
import exceptions.CommandFailedException;
import exceptions.NoSuchDataException;
import request.IRequest;
import response.IResponse;
import view.rentals.RentalsByYearView;
import view.universals.entities.UniversalEntitiesJsonView;
import view.universals.entities.UniversalEntitiesTextView;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class GetRentalsByPropertyYearCommand extends GetCommand {
	public GetRentalsByPropertyYearCommand(String path) {
		super(path, "Get Renal by Property's id, Rental's year and week.");
	}

	@Override
    public void run(IRequest request, IResponse response) throws CommandFailedException, NoSuchDataException {
        Map<String, String> binds = request.binds(getRoute());
		int id = Integer.parseInt(binds.get("pid"));
		int y = Integer.parseInt(binds.get("year"));
		List<RentalEntity> ren = null;

        SqlConnection sqlcon = null;
        try {
            sqlcon = DB.getConnection();
            ren = DbRentals.getRentalsByPropYear(sqlcon, id, y);
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

        if(ren == null || ren.isEmpty()) throw new NoSuchDataException();

        this.addView("text/plain", new UniversalEntitiesTextView(ren));
        this.addView("application/json", new UniversalEntitiesJsonView("rentals", ren));
        this.addView("text/html", new RentalsByYearView(ren, id, y));
	}

}
