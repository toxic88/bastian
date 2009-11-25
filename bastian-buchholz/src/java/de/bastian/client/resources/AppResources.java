package de.bastian.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;

public interface AppResources extends ClientBundle {

  @Source("gxt.css")
  @CssResource.NotStrict
  CssResource gxtCss();

  @Source("application.css")
  @CssResource.NotStrict
  CssResource applicationCss();

  @Source("add.png")
  ImageResource add();

  @Source("delete.png")
  ImageResource delete();

  @Source("error.png")
  ImageResource error();

  @Source("group.png")
  ImageResource group();

  @Source("lock.png")
  ImageResource lock();

  @Source("pencil.png")
  ImageResource edit();

  @Source("table.png")
  ImageResource table();

  @Source("user.png")
  ImageResource user();

  @Source("user_add.png")
  ImageResource userAdd();

  @Source("user_delete.png")
  ImageResource userDelete();

  @Source("user_edit.png")
  ImageResource userEdit();

  @Source("user_key.png")
  ImageResource userKey();

}
