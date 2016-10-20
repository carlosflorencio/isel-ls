package view.utils;

import app.entities.RentalEntity;
import utils.ResolveUrl;
import utils.libs.html.HtmlBootstrap;
import utils.libs.html.HtmlElem;
import utils.writer.Writable;

import java.util.List;

public class RentalsUtilsView extends HtmlBootstrap {

	public static Writable rentalsTable(List<RentalEntity> ren) {
		return table(toThead(), toBody(ren)).withAttr("class", "table table-striped table-hover");
	}

    private static Writable toBody(List<RentalEntity> list) {
        HtmlElem body = new HtmlElem("tbody");

        for (RentalEntity r : list) {
            HtmlElem tr = new HtmlElem("tr");

            tr.withContent(td(a(ResolveUrl.of("get.rentalsByPropertiesId", r.getIdProp()),
                                r.getIdProp() + "")));
            tr.withContent(td(a(ResolveUrl.of("get.rentalsByUsername", r.getUserName()),
                                r.getUserName() + "")));
            tr.withContent(td(a(ResolveUrl.of("get.rentalByPropertyYearWeek", r.getIdProp(), r.getYear(), r.getWeek()),
                                r.getWeek() + "")));
            tr.withContent(td(a(ResolveUrl.of("get.rentalsByPropertyYear", r.getIdProp(), r.getYear()),
                                r.getYear() + "")));
            tr.withContent(td(spanLabel(r.getState(), r.getState().equals("pending") ? "warning" : "success")));
            tr.withContent(td(r.getDataRequest().toString("H:m:s d/M/y")));
            if (r.getDataAccept() != null)
                tr.withContent(td(r.getDataAccept().toString("H:m:s d/M/y")));
            else
                tr.withContent(td(""));

            body.withContent(tr);
        }
        return body;
    }

	private static Writable toThead() {
        HtmlElem body = new HtmlElem("thead");

    	HtmlElem tr = new HtmlElem("tr");

        tr.withContent(th("Property ID"));
        tr.withContent(th("User"));
        tr.withContent(th("Week"));
        tr.withContent(th("Year"));
        tr.withContent(th("State"));
        tr.withContent(th("Request"));
        tr.withContent(th("Accept"));

        return body.withContent(tr);
	}

}
