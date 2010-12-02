package de.bastian.index.client.data;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;


public class Buzz extends JavaScriptObject {

    protected Buzz() {}

    public final native String getTitle() /*-{
        return this.title;
    }-*/;

    public final native String getUpdated() /*-{
        return this.updated;
    }-*/;

    public final native String getPublished() /*-{
        return this.title;
    }-*/;

    public final native int getRepliesCount() /*-{
        return this.links.replies.count;
    }-*/;

    public final native BuzzActor getActor() /*-{
        return this.actor;
    }-*/;

    public final native String getSource() /*-{
        return this.source;
    }-*/;

    public final native String getContent() /*-{
        return this.object.content;
    }-*/;

    public final native JsArray<BuzzAttachment> getAttachments() /*-{
        return this.object.attachments || null;
    }-*/;

    public final native BuzzLink getLikedLink() /*-{
        return this.links.liked[0];
    }-*/;

    public final native BuzzLink getAlternateLink() /*-{
        return this.links.alternate[0];
    }-*/;

    public final native BuzzLink getRepliesLink() /*-{
        return this.links.replies[0];
    }-*/;

    public final native BuzzLink getSelfLink() /*-{
        return this.links.self[0];
    }-*/;

}
