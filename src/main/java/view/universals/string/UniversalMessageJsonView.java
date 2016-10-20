package view.universals.string;

import utils.libs.json.Json;
import utils.libs.json.JsonElem;
import utils.libs.json.JsonPair;
import utils.libs.json.JsonText;

public class UniversalMessageJsonView extends Json {

    public UniversalMessageJsonView(String message) {
        super(
                new JsonElem(
                        new JsonPair("message", new JsonText(message))
                )
        );
    }


}
