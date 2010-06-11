package de.dkfz.mga.antibodydb.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.HTMLPanel;

public class NavigationView extends Composite {

  interface NavigationViewUiBinder extends UiBinder<DeckPanel, NavigationView> { }

  private static NavigationViewUiBinder uiBinder = GWT.create(NavigationViewUiBinder.class);

  @UiField DeckPanel navigation;
  @UiField HTMLPanel login;
  @UiField HTMLPanel logout;

  private NavigationView() {
    initWidget(uiBinder.createAndBindUi(this));
    navigation.showWidget(navigation.getWidgetIndex(logout));
  }

  public void showLogin() {
    navigation.showWidget(navigation.getWidgetIndex(login));
  }

  public void showLogout() {
    navigation.showWidget(navigation.getWidgetIndex(logout));
  }

}
