package view.errors;

import utils.ResolveUrl;
import view.layouts.MasterLayout;

public class InternalServerErrorHtmlView extends MasterLayout {
    public InternalServerErrorHtmlView() {
        super("500 Internal Error",

              container(
                      jumbotron(
                              row(
                                      column("col-md-12 text-center",
                                             h3(text("Yoooo nigga, my bad... I will call your girlfriend later, to let you know when i fixed this.. ;)")),
                                             img(ResolveUrl.asset("images/nigga.jpg"))
                                      )
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
