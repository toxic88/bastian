package de.bastian.gwt.fileapi.client.file;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HasHandlers;

import de.bastian.gwt.fileapi.client.file.event.AbortEvent;
import de.bastian.gwt.fileapi.client.file.event.ErrorEvent;
import de.bastian.gwt.fileapi.client.file.event.LoadEndEvent;
import de.bastian.gwt.fileapi.client.file.event.LoadEvent;
import de.bastian.gwt.fileapi.client.file.event.LoadStartEvent;
import de.bastian.gwt.fileapi.client.file.event.ProgressEvent;
import de.bastian.gwt.fileapi.client.file.exception.BrowserNotSupportedException;

/**
 * http://www.w3.org/TR/FileAPI/#FileReader-interface
 * @author Bastian Buchholz
 */
public class FileReader implements HasHandlers {

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-empty
     */
    public final static int EMPTY = 0;

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-loading
     */
    public final static int LOADING = 1;

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-done
     */
    public final static int DONE = 2;

    /**
     * http://www.w3.org/TR/FileAPI/#FileReader-interface
     */
    private JavaScriptObject impl = null;

    /**
     * Event handler for:
     * http://www.w3.org/TR/FileAPI/#event-handler-attributes-section
     */
    private HandlerManager handlers = new HandlerManager(this);

    /**
     * Checks if the browser supports the FileReader API
     * @return boolean
     */
    public final static native boolean isSupported() /*-{
        return $wnd.FileReader ? true : false;
    }-*/;

    /**
     * http://www.w3.org/TR/FileAPI/#filereaderConstrctr
     */
    public FileReader() throws BrowserNotSupportedException {
        if (FileReader.isSupported()) {
            init();
        } else {
            throw new BrowserNotSupportedException();
        }
    }

    /**
     * Create new FileReader instance
     * Passing the events to Java methods
     */
    private final native void init() /*-{
        var self = this;

        self.@de.bastian.gwt.fileapi.client.file.FileReader::impl = new $wnd.FileReader();

        self.@de.bastian.gwt.fileapi.client.file.FileReader::impl.onloadstart = $entry(function(e) {
            self.@de.bastian.gwt.fileapi.client.file.FileReader::onLoadStart(ZII)(e.lengthComputable, e.loaded, e.total);
        });

        self.@de.bastian.gwt.fileapi.client.file.FileReader::impl.onprogress = $entry(function(e) {
            self.@de.bastian.gwt.fileapi.client.file.FileReader::onProgress(ZII)(e.lengthComputable, e.loaded, e.total);
        });

        self.@de.bastian.gwt.fileapi.client.file.FileReader::impl.onload = $entry(function(e) {
            self.@de.bastian.gwt.fileapi.client.file.FileReader::onLoad(ZII)(e.lengthComputable, e.loaded, e.total);
        });

        self.@de.bastian.gwt.fileapi.client.file.FileReader::impl.onabort = $entry(function(e) {
            self.@de.bastian.gwt.fileapi.client.file.FileReader::onAbort(ZII)(e.lengthComputable, e.loaded, e.total);
        });

        self.@de.bastian.gwt.fileapi.client.file.FileReader::impl.onerror = $entry(function(e) {
            self.@de.bastian.gwt.fileapi.client.file.FileReader::onError(ZII)(e.lengthComputable, e.loaded, e.total);
        });

        self.@de.bastian.gwt.fileapi.client.file.FileReader::impl.onloadend = $entry(function(e) {
            self.@de.bastian.gwt.fileapi.client.file.FileReader::onLoadEnd(ZII)(e.lengthComputable, e.loaded, e.total);
        });
    }-*/;

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-readAsArrayBuffer
     * @param blob
     */
    public final native void readAsArrayBuffer(Blob blob) /*-{
        this.@de.bastian.gwt.fileapi.client.file.FileReader::impl &&
            this.@de.bastian.gwt.fileapi.client.file.FileReader::impl.readAsArrayBuffer(blob);
    }-*/;

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-readAsBinaryStringAsync
     * @param blob
     */
    public final native void readAsBinaryString(Blob blob) /*-{
        this.@de.bastian.gwt.fileapi.client.file.FileReader::impl &&
            this.@de.bastian.gwt.fileapi.client.file.FileReader::impl.readAsBinaryString(blob);
    }-*/;

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-readAsText
     * @param blob
     * @param encoding
     */
    public final native void readAsText(Blob blob, String encoding) /*-{
        this.@de.bastian.gwt.fileapi.client.file.FileReader::impl &&
            this.@de.bastian.gwt.fileapi.client.file.FileReader::impl.readAsText(blob, encoding);
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
        this.@de.bastian.gwt.fileapi.client.file.FileReader::impl &&
            this.@de.bastian.gwt.fileapi.client.file.FileReader::impl.readAsDataURL(blob);
    }-*/;

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-abort
     */
    public final native void abort() /*-{
        this.@de.bastian.gwt.fileapi.client.file.FileReader::impl &&
            this.@de.bastian.gwt.fileapi.client.file.FileReader::impl.abort();
    }-*/;

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-readystate
     * @return int
     */
    public final native int getReadyState() /*-{
        return this.@de.bastian.gwt.fileapi.client.file.FileReader::impl &&
                this.@de.bastian.gwt.fileapi.client.file.FileReader::impl.readyState;
    }-*/;

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-result
     * @return String / Binary
     */
    public final native Object getResult() /*-{
        return this.@de.bastian.gwt.fileapi.client.file.FileReader::impl &&
                this.@de.bastian.gwt.fileapi.client.file.FileReader::impl.result;
    }-*/;

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-error
     * @return FileError
     */
    public final native FileError getError() /*-{
        return this.@de.bastian.gwt.fileapi.client.file.FileReader::impl &&
                this.@de.bastian.gwt.fileapi.client.file.FileReader::impl.error;
    }-*/;

    // EVENTS

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-loadstart-event
     * @param lengthComputable
     * @param loaded
     * @param total
     */
    private void onLoadStart(boolean lengthComputable, int loaded, int total) {
        fireEvent(new LoadStartEvent(lengthComputable, loaded, total));
    }

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-progress-event
     * @param lengthComputable
     * @param loaded
     * @param total
     */
    private void onProgress(boolean lengthComputable, int loaded, int total) {
        fireEvent(new ProgressEvent(lengthComputable, loaded, total));
    }

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-abort-event
     * @param lengthComputable
     * @param loaded
     * @param total
     */
    private void onAbort(boolean lengthComputable, int loaded, int total) {
        fireEvent(new AbortEvent(lengthComputable, loaded, total));
    }

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-error-event
     * @param lengthComputable
     * @param loaded
     * @param total
     */
    private void onError(boolean lengthComputable, int loaded, int total) {
        fireEvent(new ErrorEvent(lengthComputable, loaded, total));
    }

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-load-event
     * @param lengthComputable
     * @param loaded
     * @param total
     */
    private void onLoad(boolean lengthComputable, int loaded, int total) {
        fireEvent(new LoadEvent(lengthComputable, loaded, total));
    }

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-loadend-event
     * @param lengthComputable
     * @param loaded
     * @param total
     */
    private void onLoadEnd(boolean lengthComputable, int loaded, int total) {
        fireEvent(new LoadEndEvent(lengthComputable, loaded, total));
    }

    // ADD HANDLERS

    public void addLoadStartHandler(LoadStartEvent.Handler handler) {
        handlers.addHandler(LoadStartEvent.TYPE, handler);
    }

    public void addProgressHandler(ProgressEvent.Handler handler) {
        handlers.addHandler(ProgressEvent.TYPE, handler);
    }

    public void addAbortHandler(AbortEvent.Handler handler) {
        handlers.addHandler(AbortEvent.TYPE, handler);
    }

    public void addErrorHandler(ErrorEvent.Handler handler) {
        handlers.addHandler(ErrorEvent.TYPE, handler);
    }

    public void addLoadHandler(LoadEvent.Handler handler) {
        handlers.addHandler(LoadEvent.TYPE, handler);
    }

    public void addLoadEndHandler(LoadEndEvent.Handler handler) {
        handlers.addHandler(LoadEndEvent.TYPE, handler);
    }

    // HasHandlers

    @Override
    public void fireEvent(GwtEvent<?> event) {
        handlers.fireEvent(event);
    }

}
