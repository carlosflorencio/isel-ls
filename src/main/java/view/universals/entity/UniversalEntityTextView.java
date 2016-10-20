package view.universals.entity;

import app.entities.body.IEntityResponsable;
import utils.Pair;
import utils.libs.text.SimpleText;
import utils.libs.text.TextPage;
import utils.writer.Writable;

public class UniversalEntityTextView extends TextPage {

    public UniversalEntityTextView(IEntityResponsable entity) {
        super(result(entity));
    }

    private static Writable result(IEntityResponsable entity) {
        if(entity == null) new SimpleText("no results");

        StringBuilder sb = new StringBuilder();

        for (Pair<String, Object> itemPair : entity.response()) {
            sb.append(itemPair.getFirst());
            sb.append( " = ");
            sb.append( itemPair.getSecond());
            sb.append("\n");
        }

        return new SimpleText(sb.toString());
    }
}
