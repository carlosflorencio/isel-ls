package view.universals.entity;

import app.entities.body.IEntityResponsable;
import utils.libs.html.HtmlElem;
import utils.libs.html.HtmlText;
import utils.writer.Writable;
import view.HtmlView;
import view.utils.UniversalUtilsView;

public class UniversalEntityHtmlView extends HtmlView {

    public UniversalEntityHtmlView(IEntityResponsable entity) {
        super("ImoProject",
                h2(new HtmlText("List result")),
                result(entity)
              );
    }

    private static Writable result(IEntityResponsable entity) {
        if(entity == null)
            return new HtmlElem("p", text("Sem resultados."));

        return UniversalUtilsView.list(entity);
    }
}