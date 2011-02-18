package de.bastian.gwt.fileapi.client.file.reader;

import com.google.gwt.core.client.JavaScriptException;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

import de.bastian.gwt.fileapi.client.file.Blob;

/**
 * http://www.w3.org/TR/FileAPI/#FileReaderSync
 * @author Bastian Buchholz
 */
public class FileReaderSync {

    /**
     * http://www.w3.org/TR/FileAPI/#FileReaderSync
     */
    private FileReaderSyncImpl impl = null;

    /**
     * Checks if the browser supports the FileReaderSync API
     * @return boolean
     */
    public final static native boolean isSupported() /*-{
        return !!$wnd.FileReaderSync;
    }-*/;

    public FileReaderSync() {
        init();
    }

    /**
     * Create new FileReaderSync instance
     */
    private final native void init() /*-{
        this.@de.bastian.gwt.fileapi.client.file.reader.FileReaderSync::impl = new $wnd.FileReaderSync();
    }-*/;

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-readAsArrayBufferSync
     * @param blob
     * @return JsArray
     */
    public final JsArray<JavaScriptObject> readAsArrayBuffer(Blob blob) throws JavaScriptException {
        return impl.readAsArrayBuffer(blob);
    };

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-readAsBinaryStringSync
     * @param blob
     * @return String
     */
    public final String readAsBinaryString(Blob blob) throws JavaScriptException {
        return impl.readAsBinaryString(blob);
    }

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-readAsTextSync
     * @param blob
     * @param encoding
     * @return String
     */
    public final String readAsText(Blob blob, String encoding) throws JavaScriptException {
        return impl.readAsText(blob, encoding);
    }

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-readAsTextSync
     * @param blob
     * @return String
     */
    public final String readAsText(Blob blob) throws JavaScriptException {
        return readAsText(blob, null);
    }

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-readAsDataURLSync
     * @param blob
     * @return String
     */
    public final String readAsDataURL(Blob blob) throws JavaScriptException {
        return impl.readAsDataURL(blob);
    }

}
