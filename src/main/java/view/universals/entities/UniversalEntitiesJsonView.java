package view.universals.entities;

import app.entities.body.IEntityResponsable;
import utils.Pair;
import utils.libs.json.*;
import utils.writer.Writable;

import java.util.List;

public class UniversalEntitiesJsonView extends Json {

    public UniversalEntitiesJsonView(String type, List<? extends IEntityResponsable> entities) {
        super(
            new JsonElem(
                    new JsonPair(type, result(entities)
                    )
            )
        );
    }

    private static Writable result(List<? extends IEntityResponsable> entities) {
        if(entities == null || entities.isEmpty()) return new JsonArr();

        JsonArr array = new JsonArr();

        for (IEntityResponsable entity : entities) {
            JsonElem elem = new JsonElem();

            for (Pair<String, Object> items : entity.response()) {
                JsonPair pair = new JsonPair(items.getFirst(), new JsonText(items.getSecond() == null ? "" : items.getSecond().toString()));
                elem.withContent(pair);
            }
            array.withContent(elem);
        }

        return array;
    }
}
