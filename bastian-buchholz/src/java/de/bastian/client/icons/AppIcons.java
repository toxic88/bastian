package de.bastian.client.icons;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;

public interface AppIcons extends ImageBundle {

  @Resource("add.png")
  AbstractImagePrototype add();

  @Resource("delete.png")
  AbstractImagePrototype delete();

  @Resource("error.png")
  AbstractImagePrototype error();

  @Resource("group.png")
  AbstractImagePrototype group();

  @Resource("lock.png")
  AbstractImagePrototype lock();

  @Resource("pencil.png")
  AbstractImagePrototype edit();

  @Resource("table.png")
  AbstractImagePrototype table();

  @Resource("user.png")
  AbstractImagePrototype user();

  @Resource("user_add.png")
  AbstractImagePrototype userAdd();

  @Resource("user_delete.png")
  AbstractImagePrototype userDelete();

  @Resource("user_edit.png")
  AbstractImagePrototype userEdit();

  @Resource("user_key.png")
  AbstractImagePrototype userKey();

}
