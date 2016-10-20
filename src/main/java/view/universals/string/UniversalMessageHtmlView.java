package view.universals.string;

import utils.libs.html.HtmlElem;
import utils.libs.html.HtmlPage;
import utils.libs.html.HtmlText;

public class UniversalMessageHtmlView extends HtmlPage {

    public UniversalMessageHtmlView(String message) {
        super("ImoProject",
              h1(new HtmlText("Output")),
              new HtmlElem("p", new HtmlText(message)
              )
        );
    }
}
