package utils.libs.json;

import utils.writer.Writable;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class JsonElem implements Writable{
	
	private final List<Writable> _content = new ArrayList<Writable>();
	public JsonElem(Writable...cs){
		
		for(Writable c : cs) {
			if(c==null) 
				_content.add(null);
			else
				_content.add(c);
        }
	}
	
	public final JsonElem withContent(Writable w) {
        _content.add(w);
        return this;
    }
	

	@Override
	public void writeTo(Writer w) throws IOException {
		int i=_content.size();
		w.write("{");
		for(Writable c : _content) {
			if(c!=null) {
				c.writeTo(w);
				if(i>1)	w.write(",");//don�t put on the end
				i--;
			}
			
        }
		w.write("}");
	}

}
