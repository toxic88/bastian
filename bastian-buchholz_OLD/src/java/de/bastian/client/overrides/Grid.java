package de.bastian.client.overrides;

import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.grid.BufferView;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.GridSelectionModel;

public class Grid<M extends ModelData> extends com.extjs.gxt.ui.client.widget.grid.Grid {

  public Grid(ListStore<M> store, ColumnModel cm) {
    this.store = store;
    this.cm = cm;
    view = new BufferView();
    focusable = true;
    baseStyle = "x-grid-panel";
    setSelectionModel(new GridSelectionModel<M>());
  }

}
