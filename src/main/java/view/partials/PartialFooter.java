package view.partials;

import utils.libs.html.HtmlBootstrap;
import utils.libs.html.HtmlElem;
import utils.libs.html.HtmlText;
import utils.writer.Writable;

public class PartialFooter extends HtmlBootstrap {

    public PartialFooter() {
        super(
                footer()
        );
    }

    private static Writable footer() {
        String msg = "© ImoProject - Carlos Florêncio & Oksana Dizdari";
        Writable text = new HtmlElem("p", new HtmlText(msg)).withAttr("class", "text-center");

        return new HtmlElem("footer", hr(), text);
    }
}
