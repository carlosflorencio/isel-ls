package app.config;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

public class Configuration {
	
	private Hashtable<String, String> table;
	private Properties file;

	public Configuration() throws IOException {
		file = new Properties();
		table = new Hashtable<String, String>();
	 
		file.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
		
		Enumeration<?> e = file.propertyNames();
		while (e.hasMoreElements()) {
			
			String key = (String) e.nextElement();
			String value = file.getProperty(key);
			table.put(key, value);
		}
	}
	
	public String load(String key) {
		return table.get(key);
	}
	
}
