package de.dkfz.mga.antibodydb.client.presenter;

import com.google.gwt.user.client.ui.Widget;
import de.dkfz.mga.antibodydb.client.view.HomeView;

public class HomePresenter implements Presenter {

  private final HomeView view;

  private static HomePresenter instance = null;

  private HomePresenter() {
    view = new HomeView(this);
  }

  public Widget asWidget() {
    return view;
  }

  public static HomePresenter get() {
    if (instance == null) {
      instance = new HomePresenter();
    }

      return instance;
    }

}
