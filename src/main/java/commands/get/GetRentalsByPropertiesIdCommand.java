package commands.get;

import app.entities.PropertyEntity;
import app.entities.RentalEntity;
import database.DB;
import database.lib.SqlConnection;
import database.models.DbProperties;
import database.models.DbRentals;
import exceptions.CommandFailedException;
import exceptions.NoSuchDataException;
import request.IRequest;
import response.IResponse;
import view.rentals.RentalsByPropertyIdView;
import view.universals.entities.UniversalEntitiesJsonView;
import view.universals.entities.UniversalEntitiesTextView;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GetRentalsByPropertiesIdCommand extends GetCommand{
	

	public GetRentalsByPropertiesIdCommand(String route) {
		super(route, "Get Rentals by Property's id.");
	}

	@Override
    public void run(IRequest request, IResponse response) throws CommandFailedException, NoSuchDataException {
        Map<String, String> binds = request.binds(getRoute());
		int id = Integer.parseInt(binds.get("pid"));
		List<RentalEntity> ren=new LinkedList<RentalEntity>();
        PropertyEntity p = null;

        SqlConnection sqlcon = null;
        try {
            sqlcon = DB.getConnection();
            ren = DbRentals.getRentalsByPropId(sqlcon, id);
            p = DbProperties.getPropertiesById(sqlcon, id);
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

        if(ren == null || p == null) throw new NoSuchDataException();
        
        addView("text/html", new RentalsByPropertyIdView(p, ren));
        addView("text/plain", new UniversalEntitiesTextView(ren));
        addView("application/json", new UniversalEntitiesJsonView("rentals", ren));

	}

}
