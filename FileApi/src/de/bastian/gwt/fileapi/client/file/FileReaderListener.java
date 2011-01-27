package de.bastian.gwt.fileapi.client.file;

/**
 * http://www.w3.org/TR/FileAPI/#event-handler-attributes-section
 * @author Bastian
 */
public abstract class FileReaderListener {

    private FileReader fileReader;

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-onloadstart
     * @param lengthComputable
     * @param loaded
     * @param total
     */
    public void onLoadStart(boolean lengthComputable, int loaded, int total) {}

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-onprogress
     * @param lengthComputable
     * @param loaded
     * @param total
     */
    public void onProgress(boolean lengthComputable, int loaded, int total) {}

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-onabort
     * @param lengthComputable
     * @param loaded
     * @param total
     */
    public void onAbort(boolean lengthComputable, int loaded, int total) {}

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-onerror
     * @param lengthComputable
     * @param loaded
     * @param total
     */
    public void onError(boolean lengthComputable, int loaded, int total) {}

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-onload
     * @param lengthComputable
     * @param loaded
     * @param total
     */
    public void onLoad(boolean lengthComputable, int loaded, int total) {}

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-onloadend
     * @param lengthComputable
     * @param loaded
     * @param total
     */
    abstract public void onLoadEnd(boolean lengthComputable, int loaded, int total);

    public final FileReader getFileReader() {
        return fileReader;
    }

    public final void setFileReader(FileReader fileReader) {
        this.fileReader = fileReader;
    }

}
