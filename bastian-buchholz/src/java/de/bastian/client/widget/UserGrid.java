package de.bastian.client.widget;

import java.util.List;
import java.util.ArrayList;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbstractImagePrototype;

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
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.Style.SelectionMode;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Record;
import com.extjs.gxt.ui.client.util.Format;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Text;
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
import de.bastian.client.model.User;
import de.bastian.client.overrides.EditorGrid;

public class UserGrid {

  private static ContentPanel grid = null;

  private UserGrid() {
  }

  public static ContentPanel get() {
    if (UserGrid.grid == null) {
      /**
       * Proxy, Reader, Loader and Store
       */
      RpcProxy<ArrayList<User>> proxy = new RpcProxy<ArrayList<User>>() {

        @Override
        protected void load(Object loadConfig, AsyncCallback<ArrayList<User>> callback) {
          Application.Services.getUserService().getAll(callback);
        }

      };

      final ListLoader<ListLoadResult<ModelData>> loader = new BaseListLoader<ListLoadResult<ModelData>>(proxy, new ModelReader());
      final ListStore<User> store = new ListStore<User>(loader);

      /**
       * Columns
       */
      ArrayList<ColumnConfig> columns = new ArrayList<ColumnConfig>();
      ColumnConfig cfg;
      cfg = new ColumnConfig("username", Application.Messages.username(), 200);
      cfg.setEditor(new CellEditor(new TextField<String>()));
      cfg.setRenderer(new GridCellRenderer<User>() {

        public Object render(User model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<User> store, Grid<User> grid) {
          return Format.htmlEncode(model.getUsername());
        }

      });
      columns.add(cfg);

      cfg = new ColumnConfig("rights", Application.Messages.rights(), 200);
      cfg.setRenderer(new GridCellRenderer<User>() {

        public Object render(User model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<User> store, Grid<User> grid) {
          String ret = "";

          if (model.isAdmin()) {
            ret += " Admin ";
          }
          if (model.isUser()) {
            ret += " User ";
          }

          return ret;
        }

      });
      columns.add(cfg);

      cfg = new ColumnConfig("createDate", Application.Messages.create_date(), 200);
      cfg.setDateTimeFormat(DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss"));
      columns.add(cfg);

      ColumnModel cm = new ColumnModel(columns);

      /**
       * EditorGrid
       */
      final EditorGrid<User> g = new EditorGrid<User>(store, cm);
      g.setBorders(true);
      g.getView().setForceFit(true);
      g.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

      /**
       * ContentPanel
       */
      ContentPanel p = new ContentPanel(new FitLayout());
      p.add(g);
      p.setHeading(Application.Messages.users());
      p.setFrame(true);
      p.setIcon(AbstractImagePrototype.create(Application.Resources.group()));

      /**
       * Toolbar
       */
      ToolBar toolBar = new ToolBar();
      toolBar.setSpacing(2);
      toolBar.add(new Text(Application.Messages.username() + ":"));
      final TextField<String> username = new TextField<String>();
      toolBar.add(username);
      toolBar.add(new Text(Application.Messages.password() + ":"));
      final TextField<String> password = new TextField<String>();
      password.setPassword(true);
      toolBar.add(password);
      toolBar.add(new Button(Application.Messages.add(), AbstractImagePrototype.create(Application.Resources.userAdd()), new SelectionListener<ButtonEvent>() {

        public void componentSelected(ButtonEvent event) {

          if (username.getValue() == null || password.getValue() == null) {
            return;
          }

          AsyncCallback<User> callback = new AsyncCallback<User>() {

            public void onFailure(Throwable caught) {
              Dispatcher.get().dispatch(Application.Events.Error.getType(), caught);
            }

            public void onSuccess(User result) {
              store.add(result);
            }

          };

          Application.Services.getUserService().createUser(username.getValue(), password.getValue(), callback);

          username.setValue(null);
          password.setValue(null);
        }

      }));
      final Button deleteSelectedUser = new Button(" ", AbstractImagePrototype.create(Application.Resources.userDelete()), new SelectionListener<ButtonEvent>() {

        @Override
        public void componentSelected(ButtonEvent ce) {
          final User user = g.getSelectionModel().getSelectedItem();

          if (user != null) {
            AsyncCallback<Void> callback = new AsyncCallback<Void>() {

              public void onFailure(Throwable caught) {
                Dispatcher.get().dispatch(Application.Events.Error.getType(), caught);
              }

              public void onSuccess(Void result) {
                store.remove(user);
              }

            };

            Application.Services.getUserService().removeUser(user, callback);
          }
        }

      });
      deleteSelectedUser.setVisible(false);
      toolBar.add(deleteSelectedUser);
      p.setTopComponent(toolBar);

      /**
       * Edit logic
       */
      g.addListener(Events.AfterEdit, new Listener<GridEvent<User>>() {

        public void handleEvent(GridEvent<User> be) {
          final Record rec = be.getRecord();

          AsyncCallback<Void> callback = new AsyncCallback<Void>() {

            public void onFailure(Throwable caught) {
              rec.reject(false);
              Dispatcher.get().dispatch(Application.Events.Error.getType(), caught);
            }

            public void onSuccess(Void result) {
              rec.commit(false);
            }

          };

          Application.Services.getUserService().updateUser((User) rec.getModel(), callback);
        }

      });
      /**
       * Load the grid for the first time
       */
      g.addListener(Events.Attach, new Listener<GridEvent<User>>() {

        private boolean isLoaded = false;

        public void handleEvent(GridEvent<User> be) {
          if (!isLoaded) {
            loader.load();
            isLoaded = true;
          }
        }

      });
      /**
       * Show or hide the delete button depending of what is selected
       */
      g.getSelectionModel().addSelectionChangedListener(new SelectionChangedListener<User>() {

        @Override
        public void selectionChanged(SelectionChangedEvent<User> se) {
          if (se.getSelectedItem() != null) {
            deleteSelectedUser.setVisible(true);
            deleteSelectedUser.setText(Application.Messages.delete() + " '" + Format.htmlEncode(se.getSelectedItem().getUsername()) + "'!");
          } else {
            deleteSelectedUser.setVisible(false);
          }
        }

      });

      grid = p;
    }

    return grid;
  }

}
