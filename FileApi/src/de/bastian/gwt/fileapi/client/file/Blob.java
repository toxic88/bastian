package de.bastian.gwt.fileapi.client.file;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * http://www.w3.org/TR/FileAPI/#blob
 * @author Bastian Buchholz
 */
public class Blob extends JavaScriptObject {

    protected Blob() {}

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-size
     * @return int
     */
    public final native int getSize() /*-{
        return this.size;
    }-*/;

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-type
     * @return String
     */
    public final native String getType() /*-{
        return this.type;
    }-*/;

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-slice
     * @param start
     * @param length
     * @param contentType
     * @return Blob
     */
    public final native Blob slice(int start, int length, String contentType) /*-{
        return this.slice(start, length, contentType);
    }-*/;

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-slice
     * @param start
     * @param length
     * @return Blob
     */
    public final Blob slice(int start, int length) {
        return slice(start, length, null);
    }

}
