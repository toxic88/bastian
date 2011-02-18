package de.bastian.gwt.fileapi.client.file.writer;

import com.google.gwt.core.client.JavaScriptException;

import de.bastian.gwt.fileapi.client.file.Blob;

/**
 * http://www.w3.org/TR/file-writer-api/#idl-def-FileWriter
 * @author Bastian Buchholz
 */
public class FileWriterImpl extends FileSaverImpl {

    protected FileWriterImpl() {}

    /**
     * http://www.w3.org/TR/file-writer-api/#widl-FileWriter-position
     * @return int
     */
    public final native int getPosition() /*-{
        return this.position;
    }-*/;

    /**
     * http://www.w3.org/TR/file-writer-api/#widl-FileWriter-length
     * @return int
     */
    public final native int getLength() /*-{
        return this.length;
    }-*/;

    /**
     * http://www.w3.org/TR/file-writer-api/#widl-FileWriter-write
     * @param data
     * @throws JavaScriptException can be cast to FileException
     */
    public final native void write(Blob data) throws JavaScriptException /*-{
        this.write(data);
    }-*/;

    /**
     * http://www.w3.org/TR/file-writer-api/#widl-FileWriter-seek
     * @param offset
     * @throws JavaScriptException can be cast to FileException
     */
    public final native void seek(int offset) throws JavaScriptException /*-{
        this.seek(offset);
    }-*/;

    /**
     * http://www.w3.org/TR/file-writer-api/#widl-FileWriter-write
     * @param size
     * @throws JavaScriptException can be cast to FileException
     */
    public final native void truncate(int size) throws JavaScriptException /*-{
        this.truncate(size);
    }-*/;

}
