package de.bastian.clan.shared;

import com.google.gwt.core.client.JavaScriptObject;

public class GuestBookEntry extends JavaScriptObject {

    protected GuestBookEntry() {}

    public final native Long getId() /*-{
        return this.id;
    }-*/;

    public final native String getName() /*-{
        return this.name;
    }-*/;

    public final native String getText() /*-{
        return this.text;
    }-*/;

}
