package de.bastian.gwt.fileapi.client.file;

import com.google.gwt.user.client.IsSupported;

/**
 * http://www.w3.org/TR/FileAPI/#FileReader-interface
 * @author Bastian Buchholz
 */
public class FileReader implements IsSupported {

    /**
     * http://www.w3.org/TR/FileAPI/#blobreader-state
     */
    public static enum ReadyState {
        /**
         * http://www.w3.org/TR/FileAPI/#dfn-empty
         */
        EMPTY,

        /**
         * http://www.w3.org/TR/FileAPI/#dfn-loading
         */
        LOADING,

        /**
         * http://www.w3.org/TR/FileAPI/#dfn-done
         */
        DONE
    }

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
        init(listener);
        listener.setFileReader(this);
    }

    /**
     * Create new FileReader instance
     * Initialize event callbacks
     */
    private final native void init(FileReaderListener listener) /*-{
        this.@de.bastian.gwt.fileapi.client.file.FileReader::impl = new $wnd.FileReader();

        this.@de.bastian.gwt.fileapi.client.file.FileReader::impl.onloadstart = function(e) {
            listener.@de.bastian.gwt.fileapi.client.file.FileReaderListener::onLoadStart(ZII)(e.lengthComputable, e.loaded, e.total);
        };

        this.@de.bastian.gwt.fileapi.client.file.FileReader::impl.onprogress = function(e) {
            listener.@de.bastian.gwt.fileapi.client.file.FileReaderListener::onProgress(ZII)(e.lengthComputable, e.loaded, e.total);
        };

        this.@de.bastian.gwt.fileapi.client.file.FileReader::impl.onload = function(e) {
            listener.@de.bastian.gwt.fileapi.client.file.FileReaderListener::onLoad(ZII)(e.lengthComputable, e.loaded, e.total);
        };

        this.@de.bastian.gwt.fileapi.client.file.FileReader::impl.onabort = function(e) {
            listener.@de.bastian.gwt.fileapi.client.file.FileReaderListener::onAbort(ZII)(e.lengthComputable, e.loaded, e.total);
        };

        this.@de.bastian.gwt.fileapi.client.file.FileReader::impl.onerror = function(e) {
            listener.@de.bastian.gwt.fileapi.client.file.FileReaderListener::onError(ZII)(e.lengthComputable, e.loaded, e.total);
        };

        this.@de.bastian.gwt.fileapi.client.file.FileReader::impl.onloadend = function(e) {
            listener.@de.bastian.gwt.fileapi.client.file.FileReaderListener::onLoadEnd(ZII)(e.lengthComputable, e.loaded, e.total);
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
    public final ReadyState getReadyState() {
        return impl.getReadyState();
    }

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-result
     * @return String / Binary
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
