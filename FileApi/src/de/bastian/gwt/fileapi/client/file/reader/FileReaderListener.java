package de.bastian.gwt.fileapi.client.file.reader;

import de.bastian.gwt.fileapi.client.file.events.ProgressEvent;

/**
 * http://www.w3.org/TR/FileAPI/#event-handler-attributes-section
 * @author Bastian Buchholz
 */
public abstract class FileReaderListener {

    /**
     * So you can reference the FileReader object..
     * Don't know if this is the right way to do it but it works
     */
    private FileReader fileReader;

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-onloadstart
     * @param event
     */
    public void onLoadStart(ProgressEvent event) {}

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-onprogress
     * @param event
     */
    public void onProgress(ProgressEvent event) {}

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-onabort
     * @param event
     */
    public void onAbort(ProgressEvent event) {}

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-onerror
     * @param event
     */
    public void onError(ProgressEvent event) {}

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-onload
     * @param event
     */
    public void onLoad(ProgressEvent event) {}

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-onloadend
     * @param event
     */
    public abstract void onLoadEnd(ProgressEvent event);

    public final FileReader getFileReader() {
        return fileReader;
    }

    public final void setFileReader(FileReader fileReader) {
        this.fileReader = fileReader;
    }

}
