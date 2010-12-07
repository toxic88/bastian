package de.bastian.index.client.data;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class BuzzFeed extends JavaScriptObject {

    protected BuzzFeed() {}

    public final native JsArray<Buzz> getBuzzes() /*-{
        return this.data.items;
    }-*/;

    public final native String getTitle() /*-{
        return this.data.title;
    }-*/;

    public final native String getUpdated() /*-{
        return this.data.updated;
    }-*/;

}
