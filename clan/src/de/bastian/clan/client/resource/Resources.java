package de.bastian.clan.client.resource;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.CssResource.NotStrict;
import com.google.gwt.resources.client.ImageResource;

public interface Resources extends ClientBundle {

    @Source("style.css")
    @NotStrict
    CssResource style();

    @Source("shadowbox.css")
    @NotStrict
    CssResource shadowbox();

    @Source("delete.png")
    ImageResource delete();

    @Source("header.jpg")
    ImageResource header();

    @Source("pencil.png")
    ImageResource pencil();

}
