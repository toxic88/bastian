package de.bastian.gwt.fileapi.client.file.writer;

import com.google.gwt.core.client.JavaScriptException;

import de.bastian.gwt.fileapi.client.file.Blob;
import de.bastian.gwt.fileapi.client.file.exceptions.FileError;

/**
 * http://www.w3.org/TR/file-writer-api/#idl-def-FileSaver
 * @author Bastian Buchholz
 */
public class FileSaver {

    /**
     * http://www.w3.org/TR/file-writer-api/#widl-FileSaver-INIT
     */
    public static final int INIT = 0;

    /**
     * http://www.w3.org/TR/file-writer-api/#widl-FileSaver-WRITING
     */
    public static final int WRITING = 1;

    /**
     * http://www.w3.org/TR/file-writer-api/#widl-FileSaver-DONE
     */
    public static final int DONE = 2;

    /**
     * http://www.w3.org/TR/file-writer-api/#idl-def-FileSaver
     */
    private FileSaverImpl impl = null;

    /**
     * Checks if the browser supports the BlobBuilder API
     * @return boolean
     */
    public final static native boolean isSupported() /*-{
        return !!$wnd.FileSaver && !!$wnd.FileWriter;
    }-*/;

    protected FileSaver(FileSaverListener listener) {
        listener.setFileSaver(this);
    }

    public FileSaver(Blob blob, FileSaverListener listener) {
        this(listener);
        init(blob, listener);
    }

    /**
     * Create new FileSaver instance
     * Initialize event callbacks
     */
    private final native void init(Blob blob, FileSaverListener listener) /*-{
        this.@de.bastian.gwt.fileapi.client.file.writer.FileSaver::impl = new $wnd.FileWriter(blob);

        this.@de.bastian.gwt.fileapi.client.file.writer.FileSaver::impl.onwritestart = function(event) {
            listener.@de.bastian.gwt.fileapi.client.file.writer.FileSaverListener::onWriteStart(Lde/bastian/gwt/fileapi/client/file/events/ProgressEvent;)(event);
        };

        this.@de.bastian.gwt.fileapi.client.file.writer.FileSaver::impl.onprogress = function(event) {
            listener.@de.bastian.gwt.fileapi.client.file.writer.FileSaverListener::onProgress(Lde/bastian/gwt/fileapi/client/file/events/ProgressEvent;)(event);
        };

        this.@de.bastian.gwt.fileapi.client.file.writer.FileSaver::impl.onwrite = function(event) {
            listener.@de.bastian.gwt.fileapi.client.file.writer.FileSaverListener::onWrite(Lde/bastian/gwt/fileapi/client/file/events/ProgressEvent;)(event);
        };

        this.@de.bastian.gwt.fileapi.client.file.writer.FileSaver::impl.onabort = function(event) {
            listener.@de.bastian.gwt.fileapi.client.file.writer.FileSaverListener::onAbort(Lde/bastian/gwt/fileapi/client/file/events/ProgressEvent;)(event);
        };

        this.@de.bastian.gwt.fileapi.client.file.writer.FileSaver::impl.onerror = function(event) {
            listener.@de.bastian.gwt.fileapi.client.file.writer.FileSaverListener::onError(Lde/bastian/gwt/fileapi/client/file/events/ProgressEvent;)(event);
        };

        this.@de.bastian.gwt.fileapi.client.file.writer.FileSaver::impl.onwriteend = function(event) {
            listener.@de.bastian.gwt.fileapi.client.file.writer.FileSaverListener::onWriteEnd(Lde/bastian/gwt/fileapi/client/file/events/ProgressEvent;)(event);
        };
    }-*/;

    /**
     * http://www.w3.org/TR/file-writer-api/#widl-FileSaver-abort
     * @throws JavaScriptException which can be cast to FileException
     */
    public final void abort() throws JavaScriptException {
        impl.abort();
    }

    /**
     * http://www.w3.org/TR/file-writer-api/#widl-FileSaver-readyState
     * @return int
     */
    public final int getReadyState() {
        return impl.getReadyState();
    }

    /**
     * http://www.w3.org/TR/file-writer-api/#widl-FileSaver-error
     * @return FileError
     */
    public final FileError getError() {
        return impl.getError();
    }

}
