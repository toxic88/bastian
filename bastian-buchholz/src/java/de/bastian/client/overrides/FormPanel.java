package de.bastian.client.overrides;

import com.extjs.gxt.ui.client.widget.form.Field;

public class FormPanel extends com.extjs.gxt.ui.client.widget.form.FormPanel {

  public void clearInvalid() {
    for (Field<?> field : getFields()) {
      field.clearInvalid();
    }
  }

}
