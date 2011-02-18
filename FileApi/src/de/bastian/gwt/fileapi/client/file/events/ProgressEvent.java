package de.bastian.gwt.fileapi.client.file.events;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * http://www.w3.org/TR/progress-events/#the-progressevent-interface
 * @author Bastian Buchholz
 */
public class ProgressEvent extends JavaScriptObject {

    protected ProgressEvent() {}

    /**
     * http://www.w3.org/TR/progress-events/#dom-progressevent-lengthcomputable
     * @return boolean
     */
    public final native boolean getLengthComputable() /*-{
        return this.lengthComputable;
    }-*/;

    /**
     * http://www.w3.org/TR/progress-events/#dom-progressevent-loaded
     * @return int
     */
    public final native int getLoaded() /*-{
        return this.loaded;
    }-*/;

    /**
     * http://www.w3.org/TR/progress-events/#dom-progressevent-total
     * @return int
     */
    public final native int getTotal() /*-{
        return this.total;
    }-*/;

}
