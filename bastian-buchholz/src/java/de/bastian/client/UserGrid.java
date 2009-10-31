package de.bastian.client;

import com.extjs.gxt.ui.client.data.BaseListLoader;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.ListLoader;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.ModelReader;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import de.bastian.client.rpc.ServiceManager;
import de.bastian.client.model.User;
import java.util.ArrayList;
import java.util.List;

public class UserGrid {

    private static Grid<User> grid = null;

    private UserGrid() {}

    public static Grid<User> get() {
        if (UserGrid.grid == null) {

            RpcProxy<List<User>> proxy = new RpcProxy<List<User>>() {
                @Override
                protected void load(Object loadConfig, AsyncCallback<List<User>> callback) {
                    ServiceManager.getUserService().getAll(callback);
                }
            };

            ModelReader reader = new ModelReader();
            ListLoader<ListLoadResult<ModelData>> loader = new BaseListLoader<ListLoadResult<ModelData>>(proxy, reader);
            ListStore<User> store = new ListStore<User>(loader);

            loader.load();

            List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
            columns.add(new ColumnConfig("username", "Username", 200));

            ColumnConfig cfg = new ColumnConfig("createDate", "Create date", 200);
            cfg.setDateTimeFormat(DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss"));
            columns.add(cfg);

            ColumnModel cm = new ColumnModel(columns);

            UserGrid.grid = new Grid<User>(store, cm);
        }

        return UserGrid.grid;
    }

}
