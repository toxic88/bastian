package de.bastian.gwt.fileapi.client.file;

/**
 * http://www.w3.org/TR/FileAPI/#event-handler-attributes-section
 * @author Bastian
 */
public abstract class FileReaderListener {

    private FileReader fileReader;

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-onloadstart
     * @param ProgressEvent
     */
    public void onLoadStart(ProgressEvent event) {}

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-onprogress
     * @param ProgressEvent
     */
    public void onProgress(ProgressEvent event) {}

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-onabort
     * @param ProgressEvent
     */
    public void onAbort(ProgressEvent event) {}

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-onerror
     * @param ProgressEvent
     */
    public void onError(ProgressEvent event) {}

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-onload
     * @param ProgressEvent
     */
    public void onLoad(ProgressEvent event) {}

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-onloadend
     * @param ProgressEvent
     */
    abstract public void onLoadEnd(ProgressEvent event);

    public final FileReader getFileReader() {
        return fileReader;
    }

    public final void setFileReader(FileReader fileReader) {
        this.fileReader = fileReader;
    }

}
