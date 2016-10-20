package commands.get;

import app.entities.RentalEntity;
import database.DB;
import database.lib.SqlConnection;
import database.models.DbRentals;
import exceptions.CommandFailedException;
import exceptions.NoSuchDataException;
import request.IRequest;
import response.IResponse;
import view.rentals.RentalsByPropertyYearWeekView;
import view.universals.entity.UniversalEntityJsonView;
import view.universals.entity.UniversalEntityTextView;

import java.sql.SQLException;
import java.util.Map;

public class GetRentalByPropertyYearWeekCommand extends GetCommand {

	public GetRentalByPropertyYearWeekCommand(String path) {
		super(path, "Get Rental by Property's id, Rental's year and week.");
	}

	@Override
    public void run(IRequest request, IResponse response) throws CommandFailedException, NoSuchDataException {
        Map<String, String> binds = request.binds(getRoute());
		int id = Integer.parseInt(binds.get("pid"));
		int y = Integer.parseInt(binds.get("year"));
		int w = Integer.parseInt(binds.get("cw"));
		RentalEntity ren = null;

        SqlConnection sqlcon = null;
        try {
            sqlcon = DB.getConnection();
            ren = DbRentals.getRentalByPropYearWeek(sqlcon, id, y, w);
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

        if(ren == null) throw new NoSuchDataException();

        addView("text/html", new RentalsByPropertyYearWeekView(ren));
        addView("text/plain", new UniversalEntityTextView(ren));
        addView("application/json", new UniversalEntityJsonView(ren));
	}

}
