package de.bastian.client.widget;

import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import de.bastian.client.model.Timetable;
import java.util.ArrayList;
import java.util.List;

public class TimeTable {

  private static ContentPanel grid = null;

  private TimeTable() {
  }

  public static ContentPanel get() {
    if (TimeTable.grid == null) {
      ListStore<Timetable> store = new ListStore<Timetable>();
      store.add(Timetable.getTimetable());

      List<ColumnConfig> columns = new ArrayList<ColumnConfig>();

      columns.add(new ColumnConfig("time",      "Time",      100));
      columns.add(new ColumnConfig("monday",    "Monday",    100));
      columns.add(new ColumnConfig("tuesday",   "Tuesday",   100));
      columns.add(new ColumnConfig("wednesday", "Wednesday", 100));
      columns.add(new ColumnConfig("thursday",  "Thursday",  100));
      columns.add(new ColumnConfig("friday",    "Friday",    100));
      columns.add(new ColumnConfig("id",        "Hour",      100));

      ColumnModel cm = new ColumnModel(columns);
      for (ColumnConfig cfg : cm.getColumns()) {
        cfg.setMenuDisabled(true);
        cfg.setSortable(false);
      }

      Grid g = new Grid(store, cm);
      g.setBorders(true);
      g.getView().setForceFit(true);

      ContentPanel p = new ContentPanel(new FitLayout());
      p.add(g);
      p.setHeading("Timetable");
      p.setFrame(true);

      TimeTable.grid = p;
    }

    return TimeTable.grid;
  }

}
