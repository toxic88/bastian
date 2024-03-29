package de.bastian.gwt.fileapi.client.file.exceptions;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * http://www.w3.org/TR/FileAPI/#dfn-FileException and http://www.w3.org/TR/file-writer-api/#idl-def-FileException
 * @author Bastian Buchholz
 */
public class FileException extends JavaScriptObject {

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

    /**
     * http://www.w3.org/TR/file-writer-api/#widl-FileException-NO_MODIFICATION_ALLOWED_ERR
     */
    public final static int NO_MODIFICATION_ALLOWED_ERR = 6;

    /**
     * http://www.w3.org/TR/file-writer-api/#widl-FileException-INVALID_STATE_ERR
     */
    public final static int INVALID_STATE_ERR = 7;

    /**
     * http://www.w3.org/TR/file-writer-api/#widl-FileException-SYNTAX_ERR
     */
    public final static int SYNTAX_ERR = 8;

    protected FileException() {}

    /**
     * http://www.w3.org/TR/FileAPI/#dfn-code-exception
     * @return int
     */
    public final native int getCode() /*-{
        return this.code;
    }-*/;

}
