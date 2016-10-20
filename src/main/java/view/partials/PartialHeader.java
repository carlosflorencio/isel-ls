package view.partials;

import utils.ResolveUrl;
import utils.libs.html.HtmlBootstrap;
import utils.libs.html.HtmlElem;
import utils.libs.html.HtmlText;

public class PartialHeader extends HtmlBootstrap {

    public PartialHeader() {
        super(
                navbar(brand(),
                       linkWithIcon("Users", ResolveUrl.ofRoute("get.users"), "user"),
                       linkWithIcon("Properties", ResolveUrl.ofRoute("get.properties"), "map-marker")
                )
        );
    }

    private static HtmlElem brand() {
        return new HtmlElem("a", new HtmlText("ImoProject")).withAttr("href", ResolveUrl.ofHome());
    }
}
