package de.bastian.client.view;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.layout.CardLayout;

import de.bastian.client.Application;
import de.bastian.client.widget.TimeTable;

public class TimetableView extends View {

  private ContentPanel timeTable;

  public TimetableView(Controller controller) {
    super(controller);
  }

  @Override
  protected void handleEvent(AppEvent event) {
    if (event.getType() == Application.Events.TimeTable.getType()) {
      ((CardLayout) getCenter().getLayout()).setActiveItem(timeTable);
    }
  }

  @Override
  protected void initialize() {
    getCenter().add(timeTable = TimeTable.get());
  }

  private static LayoutContainer getCenter() {
    return (LayoutContainer) Registry.get(Application.Keys.VIEWPORT_CENTER);
  }

}
