package de.bastian.gwt.fileapi.client.file.reader;

import com.google.gwt.core.client.JavaScriptException;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

import de.bastian.gwt.fileapi.client.file.Blob;

/**
 * http://www.w3.org/TR/FileAPI/#FileReaderSync
 * @author Bastian Buchholz
 */
public class FileReaderSyncImpl extends JavaScriptObject {

    protected FileReaderSyncImpl() {}

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-readAsArrayBufferSync
     * @param blob
     * @return JsArray
     */
    public final native JsArray<JavaScriptObject> readAsArrayBuffer(Blob blob) throws JavaScriptException /*-{
        return this.readAsArrayBuffer(blob);
    }-*/;

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-readAsBinaryStringSync
     * @param blob
     * @return String
     */
    public final native String readAsBinaryString(Blob blob) throws JavaScriptException /*-{
        return this.readAsBinaryString(blob);
    }-*/;

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-readAsTextSync
     * @param blob
     * @param encoding
     * @return String
     */
    public final native String readAsText(Blob blob, String encoding) throws JavaScriptException /*-{
        return this.readAsText(blob, encoding);
    }-*/;

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
    public final native String readAsDataURL(Blob blob) throws JavaScriptException /*-{
        return this.readAsDataURL(blob);
    }-*/;

}
