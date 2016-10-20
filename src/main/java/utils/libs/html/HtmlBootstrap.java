package utils.libs.html;

import utils.writer.Writable;

public class HtmlBootstrap extends Html {

    protected HtmlBootstrap(Writable... cs) {
       super(cs);
    }

    /*
    |--------------------------------------------------------------------------
    | Jumbotron
    |--------------------------------------------------------------------------
    */
    public static HtmlElem jumbotron(Writable... c) {
        return new HtmlElem("div", c).withAttr("class", "jumbotron");
    }

    /*
    |--------------------------------------------------------------------------
    | Navbar
    |--------------------------------------------------------------------------
    */
    public static Writable navbar(HtmlElem brandLink, Writable... links) {
        Writable body = container(
                navbarHeader(brandLink),
                navbarCollapse(links)
        );

        return new HtmlElem("div", body)
                .withAttr("class", "navbar navbar-inverse navbar-fixed-top");
    }

    private static Writable navbarCollapse(Writable... links) {
        HtmlElem ul = new HtmlElem("ul").withAttr("class", "nav navbar-nav");

        for (Writable link : links) {
            ul.withContent(new HtmlElem("li", link));
        }

        return new HtmlElem("div", ul).withAttr("class", "navbar-collapse collapse");
    }

    private static Writable navbarHeader(HtmlElem brandLink) {
        Writable bar = new HtmlElem("span").withAttr("class", "icon-bar");

        Writable button = new HtmlElem("button", bar, bar, bar)
                                .withAttr("class", "navbar-toggle")
                                .withAttr("data-toggle", "collapse")
                                .withAttr("data-target", ".navbar-collapse")
                                .withAttr("type", "button");
        Writable link =  brandLink.withAttr("class", "navbar-brand");
        return new HtmlElem("div", button, link).withAttr("class", "navbar-header");
    }

    /*
    |--------------------------------------------------------------------------
    | Links
    |--------------------------------------------------------------------------
    */
    public static HtmlElem linkWithIcon(String text, String link, String icon) {

        return new HtmlElem("a", icon(icon), new HtmlText(" " + text)).withAttr("href", link);
    }

    /*
    |--------------------------------------------------------------------------
    | Icons
    |--------------------------------------------------------------------------
    */
    public static Writable icon(String name) {
        return new HtmlElem("i").withAttr("class", "glyphicon glyphicon-" + name);
    }

    public static Writable iconText(String icon, String text) {
        Writable i = new HtmlElem("i").withAttr("class", "glyphicon glyphicon-" + icon);
        return new HtmlBlock(i, text(" " + text));
    }

    public static Writable iconTextInverse(String icon, String text) {
        Writable i = new HtmlElem("i").withAttr("class", "glyphicon glyphicon-" + icon);
        return new HtmlBlock(text(text + " "), i);
    }

    /*
    |--------------------------------------------------------------------------
    | Grid
    |--------------------------------------------------------------------------
    */
    public static Writable container(Writable... c) {
        return new HtmlElem("div", c).withAttr("class", "container");
    }

    public static Writable row(Writable... c) {
        return new HtmlElem("div", c).withAttr("class", "row");
    }

    public static Writable column(String classes, Writable... c) {
        return new HtmlElem("div", c).withAttr("class", classes);
    }

    /*
    |--------------------------------------------------------------------------
    | Panel
    |--------------------------------------------------------------------------
    */
    public static Writable panel(String color, Writable header, Writable body) {
        Writable head = div(header).withAttr("class", "panel-heading");
        Writable b = div(body).withAttr("class", "panel-body");

        return div(head, b).withAttr("class", "panel panel-" + color);
    }

    public static Writable panelTable(String color, Writable header, Writable table) {
        Writable head = div(header).withAttr("class", "panel-heading");

        return div(head, table).withAttr("class", "panel panel-" + color);
    }

    /*
    |--------------------------------------------------------------------------
    | Form
    |--------------------------------------------------------------------------
    */
    public static Writable formGroup(Writable... c) {
        return div(c).withAttr("class", "form-group");
    }

    public static Writable form(String url, String method, String classes, Writable... c) {
        return new HtmlElem("form", c).withAttr("class", classes).withAttr("method", method).withAttr("action", url);
    }

    public static Writable input(String type, String name, String id, String classes, boolean required) {
        HtmlElem input = new HtmlElem("input", false).withAttr("type", type).withAttr("name", name).withAttr("id", id).withAttr("class", "form-control " + classes);
        if(required) input.withAttr("required", "true");
        return input;
    }

    public static Writable textarea(String name, String id, String classes, int rows, boolean required) {
        HtmlElem area = new HtmlElem("textarea").withAttr("class", "form-control " + classes).withAttr("rows", rows + "").withAttr("id", id).withAttr("name", name);
        if(required) area.withAttr("required", "true");
        return area;
    }

    public static Writable label(String _for, String name, String classes) {
        return new HtmlElem("label", text(name)).withAttr("for", _for).withAttr("class", "control-label " + classes);
    }

    public static Writable submit(String name, String color) {
        return new HtmlElem("button", text(name)).withAttr("type", "submit").withAttr("class", "btn btn-" + color);
    }

    /*
    |--------------------------------------------------------------------------
    | Lists
    |--------------------------------------------------------------------------
    */
    public static Writable listGroup(Writable... content) {
        HtmlElem ul = new HtmlElem("ul").withAttr("class", "list-group");

        for (Writable c : content) {
            ul.withContent(new HtmlElem("li", c).withAttr("class", "list-group-item"));
        }

        return ul;
    }

    /*
    |--------------------------------------------------------------------------
    | Buttons
    |--------------------------------------------------------------------------
    */
    public static Writable aButton(Writable content, String url,String color) {
        return new HtmlElem("a", content).withAttr("href", url).withAttr("class", "btn btn-" + color);
    }

    /*
    |--------------------------------------------------------------------------
    | Labels & Badges
    |--------------------------------------------------------------------------
    */
    public static Writable spanLabel(String text, String color) {
        return new HtmlElem("span", text(text)).withAttr("class", "label label-" + color);
    }

    public static Writable badge(String value) {
        return new HtmlElem("span", text(value)).withAttr("class", "badge");
    }

    /*
    |--------------------------------------------------------------------------
    | Wells
    |--------------------------------------------------------------------------
    */
    public static Writable well(String size, Writable... content) {
        return new HtmlElem("div", content).withAttr("class", "well well-" + size);
    }

}
