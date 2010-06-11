package de.dkfz.mga.antibodydb.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import de.dkfz.mga.antibodydb.client.presenter.HomePresenter;

public class HomeView extends Composite {

  interface HomeViewUiBinder extends UiBinder<HTMLPanel, HomeView> { }

  private static HomeViewUiBinder uiBinder = GWT.create(HomeViewUiBinder.class);

  private HomePresenter presenter = null;

  public HomeView(HomePresenter presenter) {
    initWidget(uiBinder.createAndBindUi(this));
    this.presenter = presenter;
  }

}
