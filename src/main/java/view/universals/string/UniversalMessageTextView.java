package view.universals.string;

import utils.libs.text.SimpleText;
import utils.libs.text.TextPage;

public class UniversalMessageTextView extends TextPage {
    public UniversalMessageTextView(String message) {
        super(new SimpleText(message));
    }
}
