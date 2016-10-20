package view.rentals;

import app.entities.PropertyEntity;
import app.entities.RentalEntity;
import utils.ResolveUrl;
import utils.libs.html.HtmlBlock;
import utils.writer.Writable;
import view.layouts.MasterLayout;
import view.utils.RentalsUtilsView;

import java.util.List;

public class RentalsByPropertyIdView extends MasterLayout {

	public RentalsByPropertyIdView(PropertyEntity prop, List<RentalEntity> ren) {
        super("Rentals of Property " + prop.getId(),
              div(
                      container(
                              topLink(prop),
                              row(
                                      column("col-md-12",
                                             h3(text("Rentals of Property " + prop.getId())),
                                             table(ren)
                                      )
                              )
                      )
              ).withAttr("class", "jumbo"),
              container(
                      row(
                              column("col-md-8 col-md-offset-2", panel(prop))
                      )
              )
              );
	}

    private static Writable topLink(PropertyEntity p) {
        return row(column("col-md-12 text-right",
                          aButton(
                                  iconText("map-marker", "Property"),
                                  ResolveUrl.of("get.propertiesById", p.getId()),
                                  "danger btn-sm")
                   )
        );
    }

    private static Writable table(List<RentalEntity> rentals) {
        if(rentals == null || rentals.isEmpty()) return p(text("This property has no rentals yet.."));

        return RentalsUtilsView.rentalsTable(rentals);
    }

    private static Writable panel(PropertyEntity prop) {
        return panel("default", iconText("plus", "New Rent"), form(prop));
    }

    private static Writable form(PropertyEntity prop) {
        Writable content = new HtmlBlock(
                formGroup(
                        label("year", "Year", "col-sm-3"),
                        column("col-sm-9",
                               input("number", "year", "year", "input-sm", true))),
                formGroup(
                        label("cw", "Week", "col-sm-3"),
                        column("col-sm-9",
                               input("number", "cw", "cw", "input-sm", true))),
                formGroup(
                        column("col-sm-offset-2 col-sm-10 text-center", submit("Rent", "primary"))
                )
        );

        return form(ResolveUrl.of("post.rental", prop.getId()), "POST", "form-horizontal", content);
    }

}
