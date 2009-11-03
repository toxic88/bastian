package de.bastian.client.widget;

import java.util.ArrayList;
import java.util.List;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.extjs.gxt.ui.client.data.BaseListLoader;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.ListLoader;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.ModelReader;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import de.bastian.client.model.User;
import de.bastian.client.overrides.EditorGrid;
import de.bastian.client.rpc.ServiceManager;

public class UserGrid {

    private static EditorGrid<User> grid = null;

    private UserGrid() {}

    public static EditorGrid<User> get() {
        if (UserGrid.grid == null) {

            RpcProxy<List<User>> proxy = new RpcProxy<List<User>>() {
                @Override
                protected void load(Object loadConfig, AsyncCallback<List<User>> callback) {
                    ServiceManager.getUserService().getAll(callback);
                }
            };

            ModelReader reader = new ModelReader();
            final ListLoader<ListLoadResult<ModelData>> loader = new BaseListLoader<ListLoadResult<ModelData>>(proxy, reader);
            ListStore<User> store = new ListStore<User>(loader);

            List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
            ColumnConfig cfg;
            cfg = new ColumnConfig("username", "Username", 200);
            cfg.setEditor(new CellEditor(new TextField<String>()));
            columns.add(cfg);

            cfg = new ColumnConfig("createDate", "Create date", 200);
            cfg.setDateTimeFormat(DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss"));
            columns.add(cfg);

            ColumnModel cm = new ColumnModel(columns);

            EditorGrid<User> g = new EditorGrid<User>(store, cm);
            g.setBorders(true);
            g.addListener(Events.Attach, new Listener<GridEvent<User>>() {
                public void handleEvent(GridEvent<User> be) {
                    loader.load();
                }
            });

            UserGrid.grid = g;
        }

        return UserGrid.grid;
    }

}
