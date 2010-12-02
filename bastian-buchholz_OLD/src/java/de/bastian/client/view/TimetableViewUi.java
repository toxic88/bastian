package de.bastian.client.view;

import java.util.ArrayList;

import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;

import de.bastian.client.Application;
import de.bastian.client.model.Timetable;

public class TimetableViewUi {

  private static ContentPanel grid = null;

  private TimetableViewUi() { }

  public static ContentPanel get() {
    if (TimetableViewUi.grid == null) {
      ListStore<Timetable> store = new ListStore<Timetable>();
      store.add(Timetable.getTimetable());

      ArrayList<ColumnConfig> columns = new ArrayList<ColumnConfig>();

      columns.add(new ColumnConfig("time",      Application.Messages.time(),      100));
      columns.add(new ColumnConfig("monday",    Application.Messages.monday(),    100));
      columns.add(new ColumnConfig("tuesday",   Application.Messages.tuesday(),   100));
      columns.add(new ColumnConfig("wednesday", Application.Messages.wednesday(), 100));
      columns.add(new ColumnConfig("thursday",  Application.Messages.thursday(),  100));
      columns.add(new ColumnConfig("friday",    Application.Messages.friday(),    100));
      columns.add(new ColumnConfig("id",        Application.Messages.hour(),      100));

      ColumnModel cm = new ColumnModel(columns);
      for (ColumnConfig cfg : cm.getColumns()) {
        cfg.setMenuDisabled(true);
        cfg.setSortable(false);
        cfg.setResizable(false);
      }

      Grid g = new Grid(store, cm);
      g.setBorders(true);
      g.getView().setForceFit(true);

      ContentPanel p = new ContentPanel(new FitLayout());
      p.add(g);
      p.setHeading(Application.Messages.timetable());
      p.setFrame(true);

      grid = p;
    }

    return grid;
  }

}
