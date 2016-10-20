package view.errors;

import utils.ResolveUrl;
import view.layouts.MasterLayout;

public class NotFoundHtmlView extends MasterLayout {
    public NotFoundHtmlView() {
        super("404 Not Found",

              container(
                      jumbotron(
                              row(
                                      column("col-md-12 text-center",
                                             img(ResolveUrl.asset("images/obama.jpg")),
                                             h3(text("Yoooo nigga, you are so wrong..")))
                              )
                      ).withAttr("style", "margin-top: 30px"),
                      row(
                              column("col-md-8 col-md-offset-2 text-center",
                                     aButton(iconText("home", "Home"), ResolveUrl.ofHome(), "info")
                              )
                      )
              )
        );
    }
}
