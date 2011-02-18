package de.bastian.gwt.fileapi.client.file.writer;

import com.google.gwt.core.client.JavaScriptException;
import com.google.gwt.core.client.JavaScriptObject;

import de.bastian.gwt.fileapi.client.file.exceptions.FileError;

/**
 * http://www.w3.org/TR/file-writer-api/#idl-def-FileSaver
 * @author Bastian Buchholz
 */
public class FileSaverImpl extends JavaScriptObject {

    protected FileSaverImpl() {}

    /**
     * http://www.w3.org/TR/file-writer-api/#widl-FileSaver-abort
     * @throws JavaScriptException which can be cast to FileException
     */
    public final native void abort() throws JavaScriptException /*-{
        this.abort();
    }-*/;

    /**
     * http://www.w3.org/TR/file-writer-api/#widl-FileSaver-readyState
     * @return int
     */
    public final native int getReadyState() /*-{
        return this.readyState;
    }-*/;

    /**
     * http://www.w3.org/TR/file-writer-api/#widl-FileSaver-error
     * @return FileError
     */
    public final native FileError getError() /*-{
        return this.error;
    }-*/;

}
