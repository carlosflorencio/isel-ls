package utils;

import java.util.HashMap;
import java.util.Map;

public class MimeType {

    protected String type;
    protected String subType;
    protected Map<String, String> params;  //a dictionary of all the parameters for the media range

    /**
     * Carves up a mime-type
     * For example, the media range 'application/xhtml;q=0.5' would get parsed
     * into:
     * ('application', 'xhtml', {'q', '0.5'})
     */
    public MimeType(String mime) {
        String[] parts = mime.split(";");
        this.params = new HashMap<>();

        for (int i = 1; i < parts.length; ++i) {
            String p = parts[i];
            String[] subParts = p.split("=");
            if (subParts.length == 2) {
                this.params.put(subParts[0].trim(), subParts[1].trim());
            }
        }
        String fullType = parts[0].trim();

        // Java URLConnection class sends an Accept header that includes a
        // single "*" - Turn it into a legal wildcard.
        if (fullType.equals("*")) {
            fullType = "*/*";
        }
        String[] types = fullType.split("/");
        this.type = types[0].trim();
        this.subType = types[1].trim();
    }

    @Override
    public String toString() {
        StringBuffer s = new StringBuffer("('" + type + "', '" + subType + "', {");
        for (String k : params.keySet()) {
            s.append("'" + k + "':'" + params.get(k) + "',");
        }
        return s.append("})").toString();
    }

    @Override
    public boolean equals(Object p) {
        if(p instanceof MimeType) {
            MimeType mime = (MimeType)p;
            if( mime.type.equals(type) && mime.subType.equals(subType) )
                return true;

            if( mime.type.equals("*") && mime.subType.equals("*") ) // */* type
                return true;
        }

        if(p instanceof String)
            return equals(new MimeType((String)p));

        return false;
    }

    public int hashCode() {
        return type.hashCode() + subType.hashCode();
    }

    public String getType() {
        return type;
    }

    public String getSubType() {
        return subType;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public String getParam(String key) {
        return params.get(key);
    }
}
