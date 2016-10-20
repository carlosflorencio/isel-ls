package view.universals.entities;

import app.entities.body.IEntityResponsable;
import utils.Pair;
import utils.libs.text.SimpleText;
import utils.libs.text.TextPage;
import utils.writer.Writable;

import java.util.List;

public class UniversalEntitiesTextView extends TextPage {

    public UniversalEntitiesTextView(List<? extends IEntityResponsable> entities) {
        super(result(entities));
    }

    private static Writable result(List<? extends IEntityResponsable> entities) {
        if(entities == null || entities.isEmpty()) new SimpleText("no results");

        StringBuilder sb = new StringBuilder();

        sb.append("-------------------------------------------------------\n");
        for (IEntityResponsable entity : entities) {

            for (Pair<String, Object> itemPair : entity.response()) {
                sb.append(itemPair.getFirst());
                sb.append( " = ");
                sb.append( itemPair.getSecond());
                sb.append("\n");
            }
            sb.append("-------------------------------------------------------\n");
        }

        return new SimpleText(sb.toString());
    }
}
