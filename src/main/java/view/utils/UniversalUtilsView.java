package view.utils;

import app.entities.body.IEntityResponsable;
import utils.EntityUtils;
import utils.Pair;
import utils.libs.html.Html;
import utils.libs.html.HtmlElem;
import utils.writer.Writable;

import java.util.List;

public class UniversalUtilsView extends Html { //TODO: test this

    public static Writable list(IEntityResponsable entity) {
        HtmlElem ul = new HtmlElem("ul");
        for (Pair<String, Object> pair : entity.response()) {
            ul.withContent(
                    li(text(pair.getFirst() + ": " + pair.getSecond()))
            );
        }
        return ul;
    }
    /*
    |--------------------------------------------------------------------------
    | Table
    |--------------------------------------------------------------------------
    */

    /**
     * Converts a list of responsable entities to a html table
     * @param entities List to convert
     * @return Table with the list
     */
    public static Writable table(List<? extends IEntityResponsable> entities) {
        List<String> columns = EntityUtils.getColumns(entities);
        return table(toThead(columns), toTbody(entities));
    }

    /**
     * Gets a thead element from the columns
     * @param columns List of columns to use in the thead
     * @return Thead formated
     */
    private static Writable toThead(List<String> columns) {
        HtmlElem body = new HtmlElem("thead");

        HtmlElem tr = new HtmlElem("tr");
        for (String column : columns) {
            tr.withContent(th(column));
        }

        return body.withContent(tr);
    }

    /**
     * Gets a tbody table element from the list of entities
     * @param list Entities to parse
     * @return Tbody from the entities
     */
    private static Writable toTbody(List<? extends IEntityResponsable> list) {
        HtmlElem body = new HtmlElem("tbody");

        if(list == null || list.isEmpty() || list.get(0) == null) return body;

        for (IEntityResponsable itemList : list) {
            HtmlElem tr = new HtmlElem("tr");
            for (Pair<String, Object> item : itemList.response()) {
                if(item != null){
                    tr.withContent(td(item.getSecond().toString()));
                }
            }
            body.withContent(tr);
        }
        return body;
    }

}
