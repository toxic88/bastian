package de.bastian.gwt.fileapi.client.file.url;

import de.bastian.gwt.fileapi.client.file.Blob;

/**
 * http://www.w3.org/TR/FileAPI/#creating-revoking
 * @author Bastian Buchholz
 */
public class URL {

    /**
     * Checks if the browser supports the URL API
     * @return boolean
     */
    public final static native boolean isSupported() /*-{
        return !!$wnd.URL && !!$wnd.URL.createObjectURL;
    }-*/;

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-createObjectURL
     * @param blob
     * @return String
     */
    public final static native String createObjectURL(Blob blob) /*-{
        return $wnd.URL.createObjectURL(blob);
     }-*/;

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-revokeObjectURL
     * @param url
     */
    public final static native void revokeObjectURL(String url) /*-{
        $wnd.URL.revokeObjectURL(url);
    }-*/;

}
