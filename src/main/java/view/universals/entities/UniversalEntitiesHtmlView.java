package view.universals.entities;

import app.entities.body.IEntityResponsable;
import utils.libs.html.HtmlElem;
import utils.libs.html.HtmlText;
import utils.writer.Writable;
import view.HtmlView;
import view.utils.UniversalUtilsView;

import java.util.List;

public class UniversalEntitiesHtmlView extends HtmlView {
    public UniversalEntitiesHtmlView(List<? extends IEntityResponsable> entities) {
        super("ImoProject",
              h2(new HtmlText("List result")),
              result(entities)
        );
    }

    private static Writable result(List<? extends IEntityResponsable> entities) {
        if(entities == null || entities.isEmpty())
            return new HtmlElem("p", text("no results."));

        return UniversalUtilsView.table(entities);
    }
}
