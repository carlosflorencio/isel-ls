package view.rentals;

import app.entities.RentalEntity;
import app.entities.UserEntity;
import utils.ResolveUrl;
import utils.writer.Writable;
import view.layouts.MasterLayout;
import view.utils.RentalsUtilsView;

import java.util.List;

public class RentalsByUsernameView extends MasterLayout {
	public RentalsByUsernameView(UserEntity user, List<RentalEntity> ren) {
        super("Rentals of " + user.getName(),
              div(
                      container(
                              row(
                                      column("col-md-12", panel(ren, user))
                              )
                      )
              ).withAttr("class", "jumbo"),
              container(
                      row(
                              column("col-md-8 col-md-offset-2",
                                     p(well("sm", a(ResolveUrl.of("get.userByUsername", user.getUserName()), "Go to " + user.getUserName())))
                              )
                      )
              )
        );
	}

    private static Writable panel(List<RentalEntity> ren, UserEntity user) {
        return panelTable("default", iconText("bullhorn", "Rentals of " + user.getName()), table(ren));
    }

    private static Writable table(List<RentalEntity> rentals) {
        if(rentals == null || rentals.isEmpty()) return div(text("This user has no rentals yet..")).withAttr("class", "panel-body");

        return RentalsUtilsView.rentalsTable(rentals);
    }
}
