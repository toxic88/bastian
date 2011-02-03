package de.bastian.gwt.fileapi.client.file;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * http://www.w3.org/TR/2010/WD-progress-events-20101019/#the-progressevent-interface
 * @author Bastian
 */
public class ProgressEvent extends JavaScriptObject {

    protected ProgressEvent() {}

    /**
     * http://www.w3.org/TR/2010/WD-progress-events-20101019/#dom-progressevent-lengthcomputable
     * @return boolean
     */
    public final native boolean getLengthComputable() /*-{
        return this.lengthComputable;
    }-*/;

    /**
     * http://www.w3.org/TR/2010/WD-progress-events-20101019/#dom-progressevent-loaded
     * @return int
     */
    public final native int getLoaded() /*-{
        return this.loaded;
    }-*/;

    /**
     * http://www.w3.org/TR/2010/WD-progress-events-20101019/#dom-progressevent-total
     * @return int
     */
    public final native int getTotal() /*-{
        return this.total;
    }-*/;

}
