package de.bastian.gwt.fileapi.client.file;

import com.google.gwt.core.client.JavaScriptObject;

import de.bastian.gwt.fileapi.client.file.FileReader.ReadyState;

/**
 * http://www.w3.org/TR/FileAPI/#FileReader-interface
 * @author Bastian Buchholz
 */
public class FileReaderImpl extends JavaScriptObject {

    protected FileReaderImpl() {}

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-readAsArrayBuffer
     * @param blob
     */
    public final native void readAsArrayBuffer(Blob blob) /*-{
        this.readAsArrayBuffer(blob);
    }-*/;

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-readAsBinaryStringAsync
     * @param blob
     */
    public final native void readAsBinaryString(Blob blob) /*-{
        this.readAsBinaryString(blob);
    }-*/;

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-readAsText
     * @param blob
     * @param encoding
     */
    public final native void readAsText(Blob blob, String encoding) /*-{
        this.readAsText(blob, encoding);
    }-*/;

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-readAsText
     * @param blob
     */
    public final void readAsText(Blob blob) {
        readAsText(blob, null);
    }

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-readAsDataURL
     * @param blob
     */
    public final native void readAsDataURL(Blob blob) /*-{
        this.readAsDataURL(blob);
    }-*/;

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-abort
     */
    public final native void abort() /*-{
        this.abort();
    }-*/;

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-readystate
     * @return int
     */
    public final native ReadyState getReadyState() /*-{
        switch (this.readyState) {
            case 0:
                return @de.bastian.gwt.fileapi.client.file.FileReader.ReadyState::EMPTY;
            case 1:
                return @de.bastian.gwt.fileapi.client.file.FileReader.ReadyState::LOADING;
            case 2:
                return @de.bastian.gwt.fileapi.client.file.FileReader.ReadyState::DONE;
        }

    }-*/;

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-result
     * @return String / Binary
     */
    public final native Object getResult() /*-{
        return this.result;
    }-*/;

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-error
     * @return FileError
     */
    public final native FileError getError() /*-{
        return this.error;
    }-*/;

}
