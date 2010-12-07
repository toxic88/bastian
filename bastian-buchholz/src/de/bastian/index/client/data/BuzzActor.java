package de.bastian.index.client.data;

import com.google.gwt.core.client.JavaScriptObject;

public class BuzzActor extends JavaScriptObject {

    protected BuzzActor() {}

    public final native String getId() /*-{
        return this.id;
    }-*/;

    public final native String getName() /*-{
        return this.name;
    }-*/;

    public final native String getProfileUrl() /*-{
        return this.profileUrl;
    }-*/;

    public final native String getThumbnailUrl() /*-{
        return this.thumbnailUrl;
    }-*/;

}
