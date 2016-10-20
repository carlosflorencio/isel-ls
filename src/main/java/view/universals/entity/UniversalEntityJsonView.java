package view.universals.entity;

import app.entities.body.IEntityResponsable;
import utils.Pair;
import utils.libs.json.Json;
import utils.libs.json.JsonElem;
import utils.libs.json.JsonPair;
import utils.libs.json.JsonText;
import utils.writer.Writable;

public class UniversalEntityJsonView extends Json {

    public UniversalEntityJsonView(IEntityResponsable entity) {
        super(
            result(entity)
        );
    }

    private static Writable result(IEntityResponsable entity) {
        if(entity == null) return new JsonElem(new JsonPair("name", new JsonText("no results.")));

        JsonElem elem = new JsonElem();

        for (Pair<String, Object> items : entity.response()) {
            JsonPair pair = new JsonPair(items.getFirst(), new JsonText(items.getSecond() == null ? "" : items.getSecond().toString()));
            elem.withContent(pair);
        }

        return elem;
    }
}
