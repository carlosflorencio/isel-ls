package utils.libs.html;

import utils.writer.Writable;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class HtmlBlock implements Writable {

    private final List<Writable> _content = new ArrayList<Writable>();

    public HtmlBlock(Writable... cs) {
    	for(Writable c : cs) {
    		_content.add(c);
    	}
    }

    public final HtmlBlock withContent(Writable w) {
        _content.add(w);
        return this;
    }
    
    @Override
    public void writeTo(Writer w) throws IOException {
        for(Writable c : _content) {
            c.writeTo(w);
        }
    }
}
