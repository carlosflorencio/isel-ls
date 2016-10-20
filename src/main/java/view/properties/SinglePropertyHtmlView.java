package view.properties;

import app.entities.PropertyEntity;
import utils.ResolveUrl;
import utils.libs.html.HtmlBlock;
import utils.writer.Writable;
import view.layouts.MasterLayout;

public class SinglePropertyHtmlView extends MasterLayout {

    public SinglePropertyHtmlView(PropertyEntity p) {
        super("Property " + p.getId(),
              div(
                      container(
                              topLink(p),
                              row(
                                      column("col-md-8 col-md-offset-2",
                                             panel("default", iconText("map-marker", "Property Info"), propertyDetails(p) ))
                              )
                      )

              ).withAttr("class", "jumbo"),
              container(
                      row(
                              column("col-md-8 col-md-offset-2",
                                     h3(text("Owner")),
                                     p(well("sm", a(ResolveUrl.of("get.userByUsername", p.getOwnerName()), p.getOwnerName())))
                              )
                      )
              )
        );
    }

    private static Writable topLink(PropertyEntity p) {
        return row(column("col-md-12 text-right",
                          aButton(
                                  iconText("bullhorn", "Property Rentals"),
                                  ResolveUrl.of("get.rentalsByPropertiesId", p.getId()),
                                  "info btn-sm")
                   )
        );
    }

    /*
    |--------------------------------------------------------------------------
    | Property details
    |--------------------------------------------------------------------------
    */
    private static Writable detail(String field, String data) {
        return row(
                column("col-xs-3 text-right", strong(text(field + ":"))),
                column("col-xs-9", text(data))
        );
    }
    private static Writable propertyDetails(PropertyEntity p) {
        return new HtmlBlock(
                detail("ID", p.getId() + ""),
                detail("Type", p.getType()),
                detail("Price", p.getPrice() + "€"),
                detail("Localization", p.getLocalization()),
                detail("Description", p.getDesc())
        );
    }

}