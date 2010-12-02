package de.bastian.index.client.data;

import com.google.gwt.core.client.JavaScriptObject;


public class BuzzAttachment extends JavaScriptObject {

    protected BuzzAttachment() {}

    public final native String getType() /*-{
        return this.type;
    }-*/;

    public final native String getTitle() /*-{
        return this.title || null;
    }-*/;

    public final native String getContent() /*-{
        return this.content || null;
    }-*/;

}
