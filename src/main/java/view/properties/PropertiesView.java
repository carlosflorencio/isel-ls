package view.properties;

import app.entities.PropertyEntity;
import utils.ResolveUrl;
import utils.libs.html.HtmlBlock;
import utils.writer.Writable;
import view.layouts.MasterLayout;
import view.utils.PropertiesUtilsView;

import java.util.List;

public class PropertiesView extends MasterLayout {
	public PropertiesView(List<PropertyEntity> prop) {
        super("Properties",
              div(
                      container(
                              row(
                                      column("col-md-8 col-md-offset-2",
                                             h3(text("Properties")),
                                             list(prop)
                                      )
                              )
                      )
              ).withAttr("class", "jumbo"),
              container(
                      row(
                              column("col-md-8 col-md-offset-2", panel())
                      )
              )
        );
	}

    private static Writable list(List<PropertyEntity> props) {
        if(props == null || props.isEmpty()) return p(text("No properties yet.."));

        return PropertiesUtilsView.getList(props);
    }

    private static Writable panel() {
        return panel("default", iconText("plus", "Create a new Property"), form());
    }

    private static Writable form() {
        Writable content = new HtmlBlock(
                formGroup(
                        label("type", "Type", "col-sm-3"),
                        column("col-sm-9",
                               input("text", "type", "type", "input-sm", true))),
                formGroup(
                        label("price", "Price", "col-sm-3"),
                        column("col-sm-9",
                               input("text", "price", "price", "input-sm", true))),
                formGroup(
                        label("location", "Location", "col-sm-3"),
                        column("col-sm-9",
                               input("text", "location", "location", "input-sm", true))),
                formGroup(
                        label("description", "Description", "col-sm-3"),
                        column("col-sm-9",
                               textarea("description", "description", "", 3, true))),
                formGroup(
                        column("col-sm-offset-2 col-sm-10 text-center", submit("Create", "primary"))
                )
        );

        return form(ResolveUrl.ofRoute("post.property"), "POST", "form-horizontal", content);
    }

}
