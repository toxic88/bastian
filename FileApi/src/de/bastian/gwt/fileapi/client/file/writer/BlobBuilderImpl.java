package de.bastian.gwt.fileapi.client.file.writer;

import com.google.gwt.core.client.JavaScriptException;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

import de.bastian.gwt.fileapi.client.file.Blob;

/**
 * http://www.w3.org/TR/file-writer-api/#the-blobbuilder-interface
 * @author Bastian Buchholz
 */
public class BlobBuilderImpl extends JavaScriptObject {

    protected BlobBuilderImpl() {}

    /**
     * http://www.w3.org/TR/file-writer-api/#widl-BlobBuilder-getBlob
     * @param contentType
     * @return Blob
     */
    public final native Blob getBlob(String contentType) /*-{
        return this.getBlob(contentType);
    }-*/;

    /**
     * http://www.w3.org/TR/file-writer-api/#widl-BlobBuilder-getBlob
     * @param contentType
     * @return Blob
     */
    public final Blob getBlob() {
        return getBlob(null);
    }

    /**
     * http://www.w3.org/TR/file-writer-api/#widl-BlobBuilder-append0
     * @param text
     * @param endings
     * @throws JavaScriptException which can be casted to FileException
     */
    public final native void append(String text, String endings) throws JavaScriptException /*-{
        this.append(text, endings);
    }-*/;

    /**
     * http://www.w3.org/TR/file-writer-api/#widl-BlobBuilder-append0
     * @param text
     * @throws JavaScriptException which can be casted to FileException
     */
    public final void append(String text) throws JavaScriptException {
        append(text, null);
    }

    /**
     * http://www.w3.org/TR/file-writer-api/#widl-BlobBuilder-append1
     * @param data
     */
    public final native void append(Blob data) /*-{
        this.append(data);
    }-*/;

    /**
     * http://www.w3.org/TR/file-writer-api/#widl-BlobBuilder-append2
     * @param data
     */
    public final native void append(JsArray<JavaScriptObject> data) /*-{
        this.append(data);
    }-*/;

}
