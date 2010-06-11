package de.dkfz.mga.antibodydb.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;

public class HeaderView extends Composite {

  interface HeaderViewUiBinder extends UiBinder<HTMLPanel, HeaderView> { }

  private static HeaderViewUiBinder uiBinder = GWT.create(HeaderViewUiBinder.class);

  public HeaderView() {
    initWidget(uiBinder.createAndBindUi(this));
  }

}
