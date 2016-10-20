package view.rentals;


import app.entities.RentalEntity;
import utils.ResolveUrl;
import utils.libs.html.HtmlBlock;
import utils.writer.Writable;
import view.layouts.MasterLayout;

public class RentalsByPropertyYearWeekView extends MasterLayout {
    public RentalsByPropertyYearWeekView(RentalEntity ren) {
        super("Rental Information (" + ren.getWeek() + "/" + ren.getYear() + ")",
              div(
                      container(
                              row(
                                      column("col-md-8 col-md-offset-2",
                                             panel("default", iconText("bullhorn", "Rental Info"), rentalDetails(ren)))
                              )
                      )

              ).withAttr("class", "jumbo"),
              container(
                      row(
                              column("col-md-8 col-md-offset-2",
                                     well("sm",
                                          row(
                                                  column("col-md-4 text-center",
                                                         aButton(iconText("bullhorn", "Property Rentals"),
                                                                 ResolveUrl.of("get.rentalsByPropertiesId", ren.getIdProp()), "info")),
                                                  column("col-md-4 text-center",
                                                         aButton(iconText("bullhorn", "Rentals Of " + ren.getYear()),
                                                                 ResolveUrl.of("get.rentalsByPropertyYear", ren.getIdProp(), ren.getYear()), "warning")),
                                                  column("col-md-4 text-center",
                                                         aButton(iconText("bullhorn", ren.getUserName() + " Rentals"),
                                                                 ResolveUrl.of("get.rentalsByUsername", ren.getUserName()), "primary"))
                                          )
                                          ))
                              )
                      )
              );
    }

    /*
    |--------------------------------------------------------------------------
    | Rental details
    |--------------------------------------------------------------------------
    */
    private static Writable detail(String field, String data) {
        return row(
                column("col-xs-3 text-right", strong(text(field + ":"))),
                column("col-xs-9", text(data))
        );
    }

    private static Writable rentalDetails(RentalEntity ren) {
        return new HtmlBlock(
                detail("Property ID",  ren.getIdProp() + ""),
                detail("Week", ren.getWeek() + ""),
                detail("Year", ren.getYear() + ""),
                detail("State", ren.getState()),
                detail("Owner", ren.getUserName()),
                detail("Request", ren.getDataRequest().toString("H:m:s d/M/y")),
                detail("Accept", ren.getDataAccept() != null ? ren.getDataAccept().toString("H:m:s d/M/y") : "waiting..")
        );
    }
}