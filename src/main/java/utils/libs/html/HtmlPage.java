package utils.libs.html;

import utils.writer.Writable;

public class HtmlPage extends HtmlBootstrap{
    
    public HtmlPage(String title, Writable... c) {
        super(
                new HtmlElem("html",
                        new HtmlElem("head", new HtmlElem("title", new HtmlText(title))),
                        new HtmlElem("body", c)
                )
        );
    }
}
