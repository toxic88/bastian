package de.dkfz.mga.antibodydb.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;

public interface Resources extends ClientBundle {

  @Source("antibodydb.css")
  @CssResource.NotStrict
  CssResource antibodydb();

  @Source("dkfz_logo.gif")
  ImageResource dkfzLogo();

  @Source("add.png")
  ImageResource add();

  @Source("delete.png")
  ImageResource delete();

}
