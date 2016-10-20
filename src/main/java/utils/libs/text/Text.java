package utils.libs.text;

import server.http.HttpContent;
import utils.writer.Writable;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class Text implements HttpContent { //TODO: test this
	
	private final List<Writable> _content = new ArrayList<Writable>();
	
	public Text(Writable... cs) {
		for(Writable c : cs)
			_content.add(c);
    }

	@Override
	public void writeTo(Writer w) throws IOException {
		for(Writable c : _content)
			c.writeTo(w);;
		
	}

	@Override
	public String getMediaType() {
		return "text/plain";
	}

}
