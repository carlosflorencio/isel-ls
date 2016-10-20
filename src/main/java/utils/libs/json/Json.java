package utils.libs.json;

import server.http.HttpContent;
import utils.writer.CompositeWritable;
import utils.writer.Writable;

import java.io.IOException;
import java.io.Writer;

public class Json implements HttpContent { //TODO: test this
	
	private Writable _content;
	
	public Json(Writable... cs) {
        _content = new CompositeWritable(cs);
    }

	@Override
	public void writeTo(Writer w) throws IOException {
		_content.writeTo(w);
		
	}

	@Override
	public String getMediaType() {
		return "application/json";
	}

}
