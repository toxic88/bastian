package de.dkfz.mga.antibodydb.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import de.dkfz.mga.antibodydb.client.presenter.DkfzPresenter;

public class DkfzView extends Composite {

  interface DkfzViewUiBinder extends UiBinder<HTMLPanel, DkfzView> { }

  private static DkfzViewUiBinder uiBinder = GWT.create(DkfzViewUiBinder.class);

  private DkfzPresenter presenter = null;

  @UiField NavigationView navigation;
  @UiField DeckPanel content;

  public DkfzView(DkfzPresenter presenter) {
    initWidget(uiBinder.createAndBindUi(this));
    this.presenter = presenter;
  }

  public void show(Widget w) {
    if (content.getWidgetIndex(w) == -1) {
      content.add(w);
    }

    content.showWidget(content.getWidgetIndex(w));
  }

  public void showLogin() {
    navigation.showLogin();
  }

  public void showLogout() {
    navigation.showLogout();
  }

}
