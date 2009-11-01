package de.bastian.client.overrides;

import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.grid.BufferView;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.GridSelectionModel;

public class EditorGrid<M extends ModelData> extends com.extjs.gxt.ui.client.widget.grid.EditorGrid<M> {

    public EditorGrid(ListStore store, ColumnModel cm) {
        super(store, cm);
        setSelectionModel(new GridSelectionModel<M>());
        setTrackMouseOver(false);

        editSupport = getEditSupport();
        editSupport.bind(this);
        this.view = new BufferView();
        this.setClicksToEdit(ClicksToEdit.TWO);
    }

}
