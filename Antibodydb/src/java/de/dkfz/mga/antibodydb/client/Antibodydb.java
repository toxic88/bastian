package de.dkfz.mga.antibodydb.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.RootPanel;
import de.dkfz.mga.antibodydb.client.event.LogoutEvent;
import de.dkfz.mga.antibodydb.client.event.ShowEvent;
import de.dkfz.mga.antibodydb.client.presenter.DkfzPresenter;
import de.dkfz.mga.antibodydb.client.presenter.EditPresenter;
import de.dkfz.mga.antibodydb.client.presenter.HomePresenter;
import de.dkfz.mga.antibodydb.client.presenter.LoginPresenter;
import de.dkfz.mga.antibodydb.client.resources.Messages;
import de.dkfz.mga.antibodydb.client.resources.Resources;
import de.dkfz.mga.antibodydb.client.services.LoginService;
import de.dkfz.mga.antibodydb.client.services.LoginServiceAsync;

public class Antibodydb implements EntryPoint, ValueChangeHandler<String> {

  public static Resources Resources = GWT.create(Resources.class);
  public static Messages Messages = GWT.create(Messages.class);
  public static HandlerManager EventBus = new HandlerManager(null);
  public static LoginServiceAsync LoginService = GWT.create(LoginService.class);

  private String lastToken = null;

  public void onModuleLoad() {
    Resources.antibodydb().ensureInjected();

    RootPanel.get().add(DkfzPresenter.get().asWidget());

    History.addValueChangeHandler(this);
    History.fireCurrentHistoryState();
  }

  public void onValueChange(ValueChangeEvent<String> event) {
    String token = event.getValue();

    if (token.equals(lastToken)) {
      return;
    }

    if (token.equals("")) {
      History.newItem("home");
      return;
    }

    if (token.equals("login")) {
      EventBus.fireEvent(new ShowEvent(LoginPresenter.get()));
    } else if (token.equals("home")) {
      EventBus.fireEvent(new ShowEvent(HomePresenter.get()));
    } else if (token.equals("edit")) {
      EventBus.fireEvent(new ShowEvent(EditPresenter.get()));
    } else if (token.equals("logout")) {
      EventBus.fireEvent(new LogoutEvent());
    }

  }

}
