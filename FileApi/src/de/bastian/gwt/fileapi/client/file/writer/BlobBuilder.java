package de.bastian.gwt.fileapi.client.file.writer;

import com.google.gwt.core.client.JavaScriptException;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

import de.bastian.gwt.fileapi.client.file.Blob;

/**
 * http://www.w3.org/TR/file-writer-api/#the-blobbuilder-interface
 * @author Bastian Buchholz
 */
public class BlobBuilder {

    /**
     * http://www.w3.org/TR/file-writer-api/#the-blobbuilder-interface
     */
    private BlobBuilderImpl impl = null;

    /**
     * Checks if the browser supports the BlobBuilder API
     * @return boolean
     */
    public final static native boolean isSupported() /*-{
        return !!$wnd.BlobBuilder;
    }-*/;

    public BlobBuilder() {
        init();
    }

    /**
     * Creates a new instance of BlobBuilder
     */
    private final native void init() /*-{
        this.@de.bastian.gwt.fileapi.client.file.writer.BlobBuilder::impl = new $wnd.BlobBuilder();
    }-*/;

    /**
     * http://www.w3.org/TR/file-writer-api/#widl-BlobBuilder-getBlob
     * @param contentType
     * @return Blob
     */
    public final Blob getBlob(String contentType) {
        return impl.getBlob(contentType);
    }

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
    public final void append(String text, String endings) throws JavaScriptException {
        impl.append(text, endings);
    }

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
    public final void append(Blob data) {
        impl.append(data);
    }

    /**
     * http://www.w3.org/TR/file-writer-api/#widl-BlobBuilder-append2
     * @param data
     */
    public final void append(JsArray<JavaScriptObject> data) {
        impl.append(data);
    }

}
