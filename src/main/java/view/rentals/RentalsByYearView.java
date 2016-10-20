package view.rentals;

import app.entities.RentalEntity;
import utils.ResolveUrl;
import utils.writer.Writable;
import view.layouts.MasterLayout;
import view.utils.RentalsUtilsView;

import java.util.List;

public class RentalsByYearView extends MasterLayout {

	public RentalsByYearView(List<RentalEntity> ren, int id, int year) {
		super("Rentals of " + year,
              div(
                      container(
                              row(
                                      column("col-md-12", panel(ren, year))
                              )
                      )
              ).withAttr("class", "jumbo"),
              container(
                      row(
                              column("col-md-8 col-md-offset-2",
                                     well("sm",
                                          row(
                                                  column("col-md-6 text-center",
                                                         aButton(iconText("arrow-left", "Rentals of " + (year-1)),
                                                                 ResolveUrl.of("get.rentalsByPropertyYear", id, year -1), "danger btn-sm")),
                                                  column("col-md-6 text-center",
                                                         aButton(iconTextInverse("arrow-right", "Rentals of " + (year+1)),
                                                                 ResolveUrl.of("get.rentalsByPropertyYear", id, year +1), "danger btn-sm"))
                                          )
                                     ))
                      )
              )
			);
		}

    private static Writable panel(List<RentalEntity> ren, int year) {
        return panelTable("default", iconText("bullhorn", "Rentals of " + year), table(ren));
    }

    private static Writable table(List<RentalEntity> rentals) {
        if(rentals == null || rentals.isEmpty()) return div(text("This year has no rentals yet..")).withAttr("class", "panel-body");

        return RentalsUtilsView.rentalsTable(rentals);
    }
}
