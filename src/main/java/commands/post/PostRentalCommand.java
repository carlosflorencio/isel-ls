package commands.post;

import app.entities.RentalEntity;
import app.entities.UserEntity;
import database.DB;
import database.lib.SqlConnection;
import database.models.DbRentals;
import exceptions.CommandFailedException;
import org.joda.time.DateTime;
import request.IRequest;
import response.IResponse;
import utils.ResolveUrl;

import java.sql.SQLException;

public class PostRentalCommand extends PostCommand{

	public PostRentalCommand(String route) {
		super(route, "Create new Rental. Necessary parameters: year, cw. Also needs auth.");
		necessaryFields.add("year");
        necessaryFields.add("cw");
	}

	@Override
    public void run(IRequest request, IResponse response) throws CommandFailedException {
		

        SqlConnection sqlcon = null;
        try {
            sqlcon = DB.getConnection();

            if(!haveAuth(request, response, sqlcon)) return;

            int id = Integer.parseInt(request.binds(getRoute()).get("pid"));
            String backUrl = ResolveUrl.of("get.rentalsByPropertiesId", id);

            if (!validateNecessaryFields(request)) {
                errorView("Required fields not present.", backUrl);
                return;
            }

            int y, w;
            try {
                y=Integer.parseInt(request.getData("year"));
                w = Integer.parseInt(request.getData("cw"));
            } catch (NumberFormatException e) {
                errorView("The year and the week must be a number.", backUrl);
                return;
            }

            UserEntity user = getAuthUser(request, response, sqlcon);

            if(!this.validWeek(y, w)) {
                errorView("That week number is not valid.", backUrl);
                return;
            }

            RentalEntity exists = DbRentals.getRentalByPropYearWeek(sqlcon, id, y, w);
            if(exists != null) {
                errorView("There is already a rent request for that date.", backUrl);
                return;
            }

            RentalEntity rental = DbRentals.rental(sqlcon, id, user.getUserName(),y,w);
            setUniversalView(rental);
            response.redirect(backUrl);
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

    private boolean validWeek(int year, int week) {
        int max = new DateTime().withYear(year).weekOfWeekyear().getMaximumValue(); //using jodatime to give us the max week
        return week > 0 && week <= max;
    }

    private boolean validYear(int year) {
        int curY = new DateTime().getYear();
        return year >= curY && year < 2050;
    }

}
