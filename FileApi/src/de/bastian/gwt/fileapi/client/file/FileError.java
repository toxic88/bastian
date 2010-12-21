package de.bastian.gwt.fileapi.client.file;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * http://www.w3.org/TR/FileAPI/#FileErrorInterface
 * @author Bastian Buchholz
 */
public class FileError extends JavaScriptObject {

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-NOT_FOUND_ERR
     */
    public final static int NOT_FOUND_ERR = 1;

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-SECURITY_ERR
     */
    public final static int SECURITY_ERR = 2;

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-ABORT_ERR
     */
    public final static int ABORT_ERR = 3;

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-NOT_READABLE_ERR
     */
    public final static int NOT_READABLE_ERR = 4;

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-ENCODING_ERR
     */
    public final static int ENCODING_ERR = 5;

    protected FileError() {}

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-code-exception
     * @return int
     */
    public final native int getCode() /*-{
        return this.code;
    }-*/;

}
