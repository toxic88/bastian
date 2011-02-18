package de.bastian.gwt.fileapi.client.file.writer;

import de.bastian.gwt.fileapi.client.file.events.ProgressEvent;

/**
 *
 * @author Bastian Buchholz
 */
public abstract class FileSaverListener {

    /**
     * So you can reference the FileReader object..
     * Don't know if this is the right way to do it but it works
     */
    private FileSaver fileSaver;

    /**
     * http://www.w3.org/TR/file-writer-api/#widl-FileSaver-onwritestart
     * @param event
     */
    public void onWriteStart(ProgressEvent event) {}

    /**
     * http://www.w3.org/TR/file-writer-api/#widl-FileSaver-onprogress
     * @param event
     */
    public void onProgress(ProgressEvent event) {}

    /**
     * http://www.w3.org/TR/file-writer-api/#widl-FileSaver-onwrite
     * @param event
     */
    public void onWrite(ProgressEvent event) {}

    /**
     * http://www.w3.org/TR/file-writer-api/#widl-FileSaver-onabort
     * @param event
     */
    public void onAbort(ProgressEvent event) {}

    /**
     * http://www.w3.org/TR/file-writer-api/#widl-FileSaver-onerror
     * @param event
     */
    public void onError(ProgressEvent event) {}

    /**
     * http://www.w3.org/TR/file-writer-api/#widl-FileSaver-onwriteend
     * @param event
     */
    public abstract void onWriteEnd(ProgressEvent event);

    public final FileSaver getFileSaver() {
        return fileSaver;
    }

    public final void setFileSaver(FileSaver fileSaver) {
        this.fileSaver = fileSaver;
    }

}
