package de.bastian.index.client.data;

import com.google.gwt.core.client.JavaScriptObject;


public class BuzzLink extends JavaScriptObject {

    protected BuzzLink() {}

    public final native String getType() /*-{
        return this.type;
    }-*/;

    public final native String getUrl() /*-{
        return this.href;
    }-*/;

}
