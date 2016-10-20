package matcher;

import exceptions.NoMimeTypeCompatibleException;
import server.http.HttpContent;
import utils.MimeType;

import java.util.Map;

public class MimeMatcher {

    private static final MimeType DEFAULT_TYPE = new MimeType("text/html");

    /**
     * Finds the best command view according to an acceptType
     * @param commandViews Command Views
     * @param acceptHeader Request accept type
     * @return The command view
     * @throws NoMimeTypeCompatibleException If best view is found for that accept type
     */
    public static HttpContent bestMatch(Map<MimeType, HttpContent> commandViews, String acceptHeader) throws NoMimeTypeCompatibleException {
        HttpContent result = null;

        if(isBlank(acceptHeader) || isUniversal(acceptHeader)) { //No accept header? lets find the default view
            return getDefault(commandViews);
        }

        for (String mimeStr : acceptHeader.split(",")) {
            MimeType mime = new MimeType(mimeStr);
            result = commandViews.get(mime);

            if(result != null) {
                return result;
            }
        }

        throw new NoMimeTypeCompatibleException();
    }

    /**
     * Searches for the default view in the map
     * @param commandViews Map with the mimes
     * @return View found
     * @throws NoMimeTypeCompatibleException
     */
    private static HttpContent getDefault(Map<MimeType, HttpContent> commandViews) throws NoMimeTypeCompatibleException {
        HttpContent result = commandViews.get(DEFAULT_TYPE);

        if(result == null) throw new NoMimeTypeCompatibleException();
        return result;
    }

    /**
     * Check if the given mime is blank
     * @param s mime type
     * @return True if is blank
     */
    private static boolean isBlank(String s) {
        return s == null || "".equals(s.trim());
    }

    /**
     * Check if mimeType is universal
     * @param s mimeType
     * @return True if it is
     */
    private static boolean isUniversal(String s) {
        return s != null && s.equals("*/*");
    }
}
