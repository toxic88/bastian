package de.bastian.gwt.fileapi.client.file;

import com.google.gwt.user.client.IsSupported;

/**
 * http://www.w3.org/TR/FileAPI/#FileReader-interface
 * @author Bastian Buchholz
 */
public class FileReader implements IsSupported {

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-empty
     */
    public static final int EMPTY = 0;

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-loading
     */
    public static final int LOADING = 1;

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-done
     */
    public static final int DONE = 2;

    /**
     * http://www.w3.org/TR/FileAPI/#FileReader-interface
     */
    private FileReaderImpl impl = null;

    /**
     * Checks if the browser supports the FileReader API
     * @return boolean
     */
    public final static native boolean isSupported() /*-{
        return !!$wnd.FileReader;
    }-*/;

    /**
     * http://www.w3.org/TR/FileAPI/#filereaderConstrctr
     */
    public FileReader(FileReaderListener listener) {
        listener.setFileReader(this);
        init(listener);
    }

    /**
     * Create new FileReader instance
     * Initialize event callbacks
     */
    private final native void init(FileReaderListener listener) /*-{
        this.@de.bastian.gwt.fileapi.client.file.FileReader::impl = new $wnd.FileReader();

        this.@de.bastian.gwt.fileapi.client.file.FileReader::impl.onloadstart = function(e) {
            $wnd.console.log(e);
            listener.@de.bastian.gwt.fileapi.client.file.FileReaderListener::onLoadStart(Lde/bastian/gwt/fileapi/client/file/ProgressEvent;)(e);
        };

        this.@de.bastian.gwt.fileapi.client.file.FileReader::impl.onprogress = function(e) {
            listener.@de.bastian.gwt.fileapi.client.file.FileReaderListener::onProgress(Lde/bastian/gwt/fileapi/client/file/ProgressEvent;)(e);
        };

        this.@de.bastian.gwt.fileapi.client.file.FileReader::impl.onload = function(e) {
            listener.@de.bastian.gwt.fileapi.client.file.FileReaderListener::onLoad(Lde/bastian/gwt/fileapi/client/file/ProgressEvent;)(e);
        };

        this.@de.bastian.gwt.fileapi.client.file.FileReader::impl.onabort = function(e) {
            listener.@de.bastian.gwt.fileapi.client.file.FileReaderListener::onAbort(Lde/bastian/gwt/fileapi/client/file/ProgressEvent;)(e);
        };

        this.@de.bastian.gwt.fileapi.client.file.FileReader::impl.onerror = function(e) {
            listener.@de.bastian.gwt.fileapi.client.file.FileReaderListener::onError(Lde/bastian/gwt/fileapi/client/file/ProgressEvent;)(e);
        };

        this.@de.bastian.gwt.fileapi.client.file.FileReader::impl.onloadend = function(e) {
            listener.@de.bastian.gwt.fileapi.client.file.FileReaderListener::onLoadEnd(Lde/bastian/gwt/fileapi/client/file/ProgressEvent;)(e);
        };
    }-*/;

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-readAsArrayBuffer
     * @param blob
     */
    public final void readAsArrayBuffer(Blob blob) {
        impl.readAsArrayBuffer(blob);
    }

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-readAsBinaryStringAsync
     * @param blob
     */
    public final void readAsBinaryString(Blob blob) {
        impl.readAsBinaryString(blob);
    }

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-readAsText
     * @param blob
     * @param encoding
     */
    public final void readAsText(Blob blob, String encoding) {
        impl.readAsText(blob, encoding);
    }

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-readAsText
     * @param blob
     */
    public final void readAsText(Blob blob) {
        impl.readAsText(blob, null);
    }

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-readAsDataURL
     * @param blob
     */
    public final void readAsDataURL(Blob blob) {
        impl.readAsDataURL(blob);
    }

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-abort
     */
    public final void abort() {
        impl.abort();
    }

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-readystate
     * @return int
     */
    public final int getReadyState() {
        return impl.getReadyState();
    }

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-result
     * @return Object
     */
    public final Object getResult() {
        return impl.getResult();
    }

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-error
     * @return FileError
     */
    public final FileError getError() {
        return impl.getError();
    }

}
