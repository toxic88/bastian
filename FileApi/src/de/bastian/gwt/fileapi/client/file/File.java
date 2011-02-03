package de.bastian.gwt.fileapi.client.file;

import java.util.Date;

/**
 * http://www.w3.org/TR/FileAPI/#file
 * @author Bastian Buchholz
 */
public class File extends Blob {

    protected File() {}

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-name
     * @return String
     */
    public final native String getName() /*-{
        return this.name;
    }-*/;

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-lastModifiedDate
     * @return Date
     */
    public final native Date getLastModifiedDate() /*-{
        return this.lastModifiedDate;
    }-*/;

}
