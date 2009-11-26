package de.bastian.client.widget;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbstractImagePrototype;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.event.WindowEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormButtonBinding;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;

import de.bastian.client.Application;
import de.bastian.client.overrides.FormPanel;

public class LoginWindow {

  private static Window win = null;

  private static FormData formData = new FormData("100%");

  private LoginWindow() {
  }

  public static Window get() {
    if (LoginWindow.win == null) {
      /**
       * FormPanel
       */
      final FormPanel fp = new FormPanel();
      fp.setHeaderVisible(false);
      fp.setBorders(false);
      fp.setBodyBorder(false);
      fp.setPadding(5);
      fp.setLabelWidth(60);

      /**
       * Fields
       */
      final TextField<String> username = new TextField<String>();
      username.setFieldLabel("Username");
      username.setAllowBlank(false);
      username.setMessageTarget("tooltip");

      final TextField<String> password = new TextField<String>();
      password.setFieldLabel("Password");
      password.setAllowBlank(false);
      password.setMessageTarget("tooltip");
      password.setPassword(true);

      fp.add(username, formData);
      fp.add(password, formData);

      /**
       * Buttons
       */
      Button loginBtn = new Button("Login", new SelectionListener<ButtonEvent>() {

        @Override
        public void componentSelected(ButtonEvent ce) {

          if (username.getValue() == null || password.getValue() == null) {
            return;
          }

          AsyncCallback<Void> callback = new AsyncCallback<Void>() {

            public void onFailure(Throwable caught) {
              Dispatcher.get().dispatch(Application.Events.Error.getType(), caught);
            }

            public void onSuccess(Void result) {
              LoginWindow.get().hide();
            }

          };

          Application.Services.getUserService().login(username.getValue(), password.getValue(), callback);

          username.setValue(null);
          password.setValue(null);
          fp.clearInvalid();
        }

      });

      new FormButtonBinding(fp).addButton(loginBtn);

      Button cancelBtn = new Button("Cancel", new SelectionListener<ButtonEvent>() {

        @Override
        public void componentSelected(ButtonEvent ce) {
          LoginWindow.get().hide();
        }

      });

      /**
       * Window
       */
      Window w = new Window();

      w.setLayout(new FitLayout());
      w.setIcon(AbstractImagePrototype.create(Application.Resources.lock()));

      w.addButton(loginBtn);
      w.addButton(cancelBtn);

      w.add(fp);

      w.setHeading("Login");
      w.setModal(true);
      w.setFocusWidget(username);
      w.setDraggable(false);
      w.setResizable(false);
      w.setWidth(250);
      w.setHeight(126);

      /**
       * Listeners
       */
      w.addListener(Events.BeforeShow, new Listener<WindowEvent>() {

        @Override
        public void handleEvent(WindowEvent be) {
          username.setValue(null);
          password.setValue(null);
          fp.clearInvalid();
        }

      });
      w.addListener(Events.Hide, new Listener<WindowEvent>() {

        public void handleEvent(WindowEvent be) {
          History.back();
        }

      });

      win = w;
    }

    return win;
  }

}
