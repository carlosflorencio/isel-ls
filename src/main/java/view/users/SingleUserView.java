package view.users;

import app.entities.PropertyEntity;
import app.entities.UserEntity;
import utils.ResolveUrl;
import utils.libs.html.HtmlBlock;
import utils.writer.Writable;
import view.layouts.MasterLayout;
import view.utils.PropertiesUtilsView;

import java.util.List;

public class SingleUserView extends MasterLayout {

    public SingleUserView(UserEntity u, List<PropertyEntity> p) {
        super(u.getName(),
              div(
                  container(
                          topLink(u),
                          row(
                                  column("col-md-8 col-md-offset-2",
                                         panel("default", iconText("user", "User Info"), userDetails(u) ))
                          )
                  )

              ).withAttr("class", "jumbo"),
              container(
                      row(
                              column("col-md-8 col-md-offset-2",
                                     h3(text("User Properties")),
                                     propertiesList(p)
                              )
                      )
              )
              );
    }

    private static Writable topLink(UserEntity u) {
        return row(column("col-md-12 text-right",
                          aButton(
                                  iconText("bullhorn", "User Rentals"),
                                  ResolveUrl.of("get.rentalsByUsername", u.getUserName()),
                                  "info btn-sm")
                   )
        );
    }

    /*
    |--------------------------------------------------------------------------
    | User details
    |--------------------------------------------------------------------------
    */
    private static Writable detail(String field, String data) {
        return row(
                column("col-xs-3 text-right", strong(text(field + ":"))),
                column("col-xs-9", text(data))
        );
    }
    private static Writable userDetails(UserEntity u) {
        return new HtmlBlock(
                detail("ID", u.getId() + ""),
                detail("Username", u.getUserName()),
                detail("Email", u.getEmail()),
                detail("Name", u.getName())
        );
    }

    /*
    |--------------------------------------------------------------------------
    | Properties
    |--------------------------------------------------------------------------
    */
    private static Writable propertiesList(List<PropertyEntity> p) {
        if(p == null || p.isEmpty()) return p(text("This user does not have any properties yet.."));

        return PropertiesUtilsView.getList(p);
    }



}
