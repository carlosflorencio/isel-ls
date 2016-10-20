package utils.libs.text;

import utils.writer.Writable;

import java.io.IOException;
import java.io.Writer;

public class SimpleText implements Writable  {
	String text;
	
	public SimpleText(String text){
        this.text = text;
	}

    @Override
    public void writeTo(Writer w) throws IOException {
        w.write(text);
    }
}
