package de.bastian.gwt.fileapi.client.file.writer;

import com.google.gwt.core.client.JavaScriptException;

import de.bastian.gwt.fileapi.client.file.Blob;

/**
 * http://www.w3.org/TR/file-writer-api/#idl-def-FileWriter
 * @author Bastian Buchholz
 */
public class FileWriter extends FileSaver {

    /**
     * http://www.w3.org/TR/file-writer-api/#idl-def-FileWriter
     */
    private FileWriterImpl impl = null;

    public FileWriter(FileSaverListener listener) {
        super(listener);
        init();
    }

    /**
     * Create new FileWriter instance
     * Initialize event callbacks
     */
    private final native void init() /*-{
        this.@de.bastian.gwt.fileapi.client.file.writer.FileWriter::impl = new $wnd.FileWriter();

        this.@de.bastian.gwt.fileapi.client.file.writer.FileWriter::impl.onwritestart = function(event) {
            listener.@de.bastian.gwt.fileapi.client.file.writer.FileSaverListener::onWriteStart(Lde/bastian/gwt/fileapi/client/file/events/ProgressEvent;)(event);
        };

        this.@de.bastian.gwt.fileapi.client.file.writer.FileWriter::impl.onprogress = function(event) {
            listener.@de.bastian.gwt.fileapi.client.file.writer.FileSaverListener::onProgress(Lde/bastian/gwt/fileapi/client/file/events/ProgressEvent;)(event);
        };

        this.@de.bastian.gwt.fileapi.client.file.writer.FileWriter::impl.onwrite = function(event) {
            listener.@de.bastian.gwt.fileapi.client.file.writer.FileSaverListener::onWrite(Lde/bastian/gwt/fileapi/client/file/events/ProgressEvent;)(event);
        };

        this.@de.bastian.gwt.fileapi.client.file.writer.FileWriter::impl.onabort = function(event) {
            listener.@de.bastian.gwt.fileapi.client.file.writer.FileSaverListener::onAbort(Lde/bastian/gwt/fileapi/client/file/events/ProgressEvent;)(event);
        };

        this.@de.bastian.gwt.fileapi.client.file.writer.FileWriter::impl.onerror = function(event) {
            listener.@de.bastian.gwt.fileapi.client.file.writer.FileSaverListener::onError(Lde/bastian/gwt/fileapi/client/file/events/ProgressEvent;)(event);
        };

        this.@de.bastian.gwt.fileapi.client.file.writer.FileWriter::impl.onwriteend = function(event) {
            listener.@de.bastian.gwt.fileapi.client.file.writer.FileSaverListener::onWriteEnd(Lde/bastian/gwt/fileapi/client/file/events/ProgressEvent;)(event);
        };
    }-*/;

    /**
     * http://www.w3.org/TR/file-writer-api/#widl-FileWriter-position
     * @return int
     */
    public final int getPosition() {
        return impl.getPosition();
    }

    /**
     * http://www.w3.org/TR/file-writer-api/#widl-FileWriter-length
     * @return int
     */
    public final int getLength() {
        return impl.getLength();
    }

    /**
     * http://www.w3.org/TR/file-writer-api/#widl-FileWriter-write
     * @param data
     * @throws JavaScriptException can be cast to FileException
     */
    public final void write(Blob data) throws JavaScriptException {
        impl.write(data);
    }

    /**
     * http://www.w3.org/TR/file-writer-api/#widl-FileWriter-seek
     * @param offset
     * @throws JavaScriptException can be cast to FileException
     */
    public final void seek(int offset) throws JavaScriptException {
        impl.seek(offset);
    }

    /**
     * http://www.w3.org/TR/file-writer-api/#widl-FileWriter-write
     * @param size
     * @throws JavaScriptException can be cast to FileException
     */
    public final void truncate(int size) throws JavaScriptException {
        impl.truncate(size);
    }

}
