package de.bastian.client.view;

import com.google.gwt.user.client.ui.RootPanel;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.HtmlContainer;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;

import de.bastian.client.Application;
import de.bastian.client.widget.UserGrid;

public class AppView extends View {

  private Viewport viewport;

  private LayoutContainer center;

  private LayoutContainer west;

  public AppView(Controller controller) {
    super(controller);
  }

  private void initUI() {
    this.viewport = new Viewport();
    this.viewport.setLayout(new BorderLayout());

    this.createNorth();
    this.createCenter();
    this.createWest();

    Registry.register(Application.Keys.VIEWPORT_CENTER, this.center);
    Registry.register(Application.Keys.VIEWPORT_WEST, this.west);

    RootPanel.get().add(this.viewport);
  }

  private void createNorth() {
    HtmlContainer northPanel = new HtmlContainer("<h1 class='header'>Bastian's Homepage</h1>");

    BorderLayoutData data = new BorderLayoutData(LayoutRegion.NORTH, 33);
    data.setMargins(new Margins());
    this.viewport.add(northPanel, data);
  }

  private void createCenter() {
    this.center = new LayoutContainer(new FitLayout());

    this.center.add(UserGrid.get()); // TODO: Should be dynamically!

    BorderLayoutData data = new BorderLayoutData(LayoutRegion.CENTER);
    data.setMargins(new Margins(5, 5, 5, 5));

    this.viewport.add(this.center, data);
  }

  private void createWest() {
    this.west = new LayoutContainer(new FitLayout());

    ContentPanel p = new ContentPanel();
    p.addText("<a href='#" + Application.Events.Login + "'>Login</a>");
    p.setHeading("Navigation");
    p.setFrame(true);
    this.west.add(p);

    BorderLayoutData data = new BorderLayoutData(LayoutRegion.WEST);
    data.setMargins(new Margins(5, 0, 5, 5));

    this.viewport.add(this.west, data);
  }

  @Override
  protected void handleEvent(AppEvent event) {
    if (event.getType() == Application.Events.Init.getType()) {
      this.initUI();
    }
  }

}
