package view.errors;

import view.layouts.MasterLayout;

public class InputErrorHtmlView extends MasterLayout {

    public InputErrorHtmlView(String message, String backUrl) {
        super("Ups...",

              container(
                      jumbotron(
                              row(
                                      column("col-md-12 text-center", h3(strong(text("Error: ")) ,text(message)))
                              )
                      ).withAttr("style", "margin-top: 30px"),
                      row(
                              column("col-md-8 col-md-offset-2 text-center",
                                     aButton(iconText("arrow-left", "Back"), backUrl, "info")
                              )
                      )
                    )
        );
    }
}
