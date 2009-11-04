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
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.SelectionMode;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Record;
import com.extjs.gxt.ui.client.util.Format;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;

import de.bastian.client.Application;
import de.bastian.client.AppEvents;
import de.bastian.client.AppRegistryKeys;
import de.bastian.client.UserManagerAsync;
import de.bastian.client.model.User;
import de.bastian.client.overrides.EditorGrid;
import de.bastian.client.rpc.RpcException;
import de.bastian.client.rpc.ServiceManager;

public class UserGrid {

  private static ContentPanel grid = null;

  private UserGrid() {
  }

  public static ContentPanel get() {
    if (UserGrid.grid == null) {
      /**
       * Proxy, Reader, Loader and Store
       */
      RpcProxy<List<User>> proxy = new RpcProxy<List<User>>() {

        @Override
        protected void load(Object loadConfig, AsyncCallback<List<User>> callback) {
          ServiceManager.getUserService().getAll(callback);
        }

      };

      ModelReader reader = new ModelReader();
      final ListLoader<ListLoadResult<ModelData>> loader = new BaseListLoader<ListLoadResult<ModelData>>(proxy, reader);
      final ListStore<User> store = new ListStore<User>(loader);

      /**
       * Columns
       */
      List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
      ColumnConfig cfg;
      cfg = new ColumnConfig("username", "Username", 200);
      cfg.setEditor(new CellEditor(new TextField<String>()));
      cfg.setRenderer(new GridCellRenderer<User>() {

        public Object render(User model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<User> store, Grid<User> grid) {
          return Format.htmlEncode((String) model.get(property));
        }

      });
      columns.add(cfg);

      cfg = new ColumnConfig("createDate", "Create date", 200);
      cfg.setDateTimeFormat(DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss"));
      columns.add(cfg);

      ColumnModel cm = new ColumnModel(columns);

      /**
       * EditorGrid
       */
      final EditorGrid<User> g = new EditorGrid<User>(store, cm);
      g.setBorders(true);
      g.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

      /**
       * Listeners
       */
      g.addListener(Events.AfterEdit, new Listener<GridEvent<User>>() {

        public void handleEvent(GridEvent<User> be) {
          final Record rec = be.getRecord();

          AsyncCallback<Void> callback = new AsyncCallback<Void>() {

            public void onFailure(Throwable caught) {
              if (caught instanceof RpcException) {
                Dispatcher.get().dispatch(AppEvents.Error, ((RpcException) caught).getError());
              }
              rec.reject(false);
            }

            public void onSuccess(Void result) {
              rec.commit(false);
            }

          };

          UserManagerAsync userService = (UserManagerAsync) Registry.get(AppRegistryKeys.SERVICES_USER);
          userService.updateUser((User) rec.getModel(), callback);
        }

      });
      g.addListener(Events.Attach, new Listener<GridEvent<User>>() {

        public void handleEvent(GridEvent<User> be) {
          loader.load();
        }

      });

      /**
       * ContentPanel
       */
      ContentPanel p = new ContentPanel(new FitLayout());
      p.add(g);
      p.setHeading("Users");
      p.setFrame(true);

      /**
       * Toolbar
       */
      ToolBar toolBar = new ToolBar();
      toolBar.setSpacing(2);
      final TextField<String> username = new TextField<String>();
      toolBar.add(username);
      final TextField<String> password = new TextField<String>();
      password.setPassword(true);
      toolBar.add(password);
      toolBar.add(new Button("Add", new SelectionListener<ButtonEvent>() {

        public void componentSelected(ButtonEvent event) {

          if (username.getValue() == null || password.getValue() == null) {
            return;
          }

          AsyncCallback<Void> callback = new AsyncCallback<Void>() {

            public void onFailure(Throwable caught) {
                if (caught instanceof RpcException) {
                  Dispatcher.get().dispatch(AppEvents.Error, ((RpcException) caught).getError());
                }
                username.setValue(null);
                password.setValue(null);
            }

            public void onSuccess(Void result) {
              loader.load();
            }

          };

          UserManagerAsync userService = (UserManagerAsync) Registry.get(AppRegistryKeys.SERVICES_USER);
          userService.createUser(username.getValue(), password.getValue(), callback);

          username.setValue(null);
          password.setValue(null);
        }

      }));
      toolBar.add(new Button("Delete selected user", new SelectionListener<ButtonEvent>() {

        @Override
        public void componentSelected(ButtonEvent ce) {
          final User user = g.getSelectionModel().getSelectedItem();
          if (user != null) {
            AsyncCallback<Void> callback = new AsyncCallback<Void>() {

              public void onFailure(Throwable caught) {
                  if (caught instanceof RpcException) {
                    Dispatcher.get().dispatch(AppEvents.Error, ((RpcException) caught).getError());
                  }
              }

              public void onSuccess(Void result) {
                store.remove(user);
              }

            };

            UserManagerAsync userService = (UserManagerAsync) Registry.get(AppRegistryKeys.SERVICES_USER);
            userService.removeUser(user, callback);
          }
        }

      }));
      p.setTopComponent(toolBar);

      UserGrid.grid = p;
    }

    return UserGrid.grid;
  }

}
