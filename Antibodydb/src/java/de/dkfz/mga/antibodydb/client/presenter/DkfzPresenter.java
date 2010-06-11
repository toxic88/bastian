package de.dkfz.mga.antibodydb.client.presenter;

import com.google.gwt.user.client.ui.Widget;
import de.dkfz.mga.antibodydb.client.Antibodydb;
import de.dkfz.mga.antibodydb.client.event.LoginEvent;
import de.dkfz.mga.antibodydb.client.event.LoginEventHandler;
import de.dkfz.mga.antibodydb.client.event.LogoutEvent;
import de.dkfz.mga.antibodydb.client.event.LogoutEventHandler;
import de.dkfz.mga.antibodydb.client.event.ShowEvent;
import de.dkfz.mga.antibodydb.client.event.ShowEventHandler;
import de.dkfz.mga.antibodydb.client.view.DkfzView;

public class DkfzPresenter implements Presenter {

  private final DkfzView view;

  private static DkfzPresenter instance = null;

  private DkfzPresenter() {
    view = new DkfzView(this);

    Antibodydb.EventBus.addHandler(ShowEvent.TYPE, new ShowEventHandler() {
      public void onShow(ShowEvent event) {
        show(event.getPresenter().asWidget());
      }
    });

    Antibodydb.EventBus.addHandler(LoginEvent.TYPE, new LoginEventHandler() {
      public void onShow(LoginEvent event) {
        view.showLogin();
      }
    });

    Antibodydb.EventBus.addHandler(LogoutEvent.TYPE, new LogoutEventHandler() {
      public void onShow(LogoutEvent event) {
        view.showLogout();
      }
    });

  }

  public void show(Widget w) {
    view.show(w);
  }

  public Widget asWidget() {
    return view;
  }

  public static DkfzPresenter get() {
    if (DkfzPresenter.instance == null) {
      DkfzPresenter.instance = new DkfzPresenter();
    }

    return DkfzPresenter.instance;
  }

}
