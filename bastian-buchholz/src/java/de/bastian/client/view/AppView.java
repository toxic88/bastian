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
import com.extjs.gxt.ui.client.widget.layout.CardLayout;

import de.bastian.client.Application;

public class AppView extends View {

  public static Viewport viewport;

  public static LayoutContainer center;

  public static LayoutContainer west;

  public AppView(Controller controller) {
    super(controller);
  }

  private void initUI() {
    viewport = new Viewport();
    viewport.setLayout(new BorderLayout());

    createNorth();
    createCenter();
    createWest();

    RootPanel.get().add(viewport);
  }

  private void createNorth() {
    HtmlContainer northPanel = new HtmlContainer("<h1 class='header'>Bastian's Homepage</h1>");

    BorderLayoutData data = new BorderLayoutData(LayoutRegion.NORTH, 33);
    data.setMargins(new Margins());
    viewport.add(northPanel, data);
  }

  private void createCenter() {
    center = new LayoutContainer(new CardLayout());

    /**
     * Default content
     */
    ContentPanel p = new ContentPanel();
    p.setFrame(true);
    p.setHeading(Application.Messages.welcome());
    p.addText(Application.Messages.welcome());

    center.add(p);

    BorderLayoutData data = new BorderLayoutData(LayoutRegion.CENTER);
    data.setMargins(new Margins(5, 5, 5, 5));

    viewport.add(center, data);
  }

  private void createWest() {
    /**
     * Create the navigation
     */
    west = NavigationViewUi.get();

    BorderLayoutData data = new BorderLayoutData(LayoutRegion.WEST);
    data.setMargins(new Margins(5, 0, 5, 5));
    viewport.add(west, data);
  }

  @Override
  protected void handleEvent(AppEvent event) {
    if (event.getType() == Application.Events.Init.getType()) {
      initUI();
    }
  }

}
