package de.bastian.client.controller;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;

import de.bastian.client.Application;
import de.bastian.client.view.TimetableView;

public class TimetableController extends Controller {

  private TimetableView timetableView;

  public TimetableController() {
    registerEventTypes(Application.Events.TimeTable.getType());
  }

  @Override
  protected void initialize() {
    timetableView = new TimetableView(this);
  }

  @Override
  public void handleEvent(AppEvent event) {
    if (event.getType() == Application.Events.TimeTable.getType()) {
      this.forwardToView(timetableView, event);
    }
  }

}
