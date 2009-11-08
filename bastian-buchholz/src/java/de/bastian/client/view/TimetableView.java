package de.bastian.client.view;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;

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
      LayoutContainer center = (LayoutContainer) Registry.get(Application.Keys.VIEWPORT_CENTER);
      if (this.timeTable.getParent() != center) {
        center.removeAll();
        center.add(this.timeTable);
        center.layout();
      }
    }
  }

  @Override
  protected void initialize() {
    this.timeTable = TimeTable.get();
  }

}
