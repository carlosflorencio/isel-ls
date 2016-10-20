package view.layouts;

import utils.ResolveUrl;
import utils.libs.html.HtmlBlock;
import utils.libs.html.HtmlBootstrap;
import utils.libs.html.HtmlElem;
import utils.libs.html.HtmlText;
import utils.writer.Writable;
import view.partials.PartialFooter;
import view.partials.PartialHeader;

public class MasterLayout extends HtmlBootstrap {
    public MasterLayout(String title, Writable... content) {
        super(
            new HtmlElem("html",
                         head(title + " - ImoProject"),
                         body(content)
            )
        );
    }

    protected static Writable body(Writable... content) {
        Writable header = new PartialHeader();
        Writable footer = new PartialFooter();

        HtmlElem wrap = new HtmlElem("div", header).withAttr("class", "wrap");
        for (Writable c : content) {
            wrap.withContent(c);
        }

        return new HtmlElem("body", wrap, footer).withContent(scripts());
    }

    protected static Writable head(String title) {
        Writable t = new HtmlElem("title", new HtmlText(title));
        Writable meta = new HtmlElem("meta", false).withAttr("charset", "utf-8");
        Writable view = new HtmlElem("meta", false)
                .withAttr("name", "viewport")
                .withAttr("content", "width=device-width, initial-scale=1"); //Bootstrap responsive
        Writable bootrap = css("https://netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css");
        Writable bootrapTheme = css("http://bootswatch.com/slate/bootstrap.min.css");
        Writable mainCss = css(ResolveUrl.asset("css/main.css"));

        return new HtmlElem("head", meta, view, t, bootrap, bootrapTheme, mainCss);
    }

    protected static Writable scripts() {
        Writable jquery = js("//code.jquery.com/jquery-1.11.0.min.js");
        Writable bootrap = js("//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js");

        return new HtmlBlock(jquery, bootrap);
    }
}
