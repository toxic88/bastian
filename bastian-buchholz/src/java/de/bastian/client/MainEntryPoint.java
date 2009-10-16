package de.bastian.client;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import java.util.List;

public class MainEntryPoint implements EntryPoint {

    final FormPanel fp = new FormPanel();
    final HTML ouputLabel = new HTML();
    final TextField<String> firstName = new TextField<String>();
    final TextField<String> lastName = new TextField<String>();

    public void onModuleLoad() {

        Button createUserBtn = new Button("Create new User!", new SelectionListener<ButtonEvent>() {
            public void componentSelected(ButtonEvent event) {

                if (firstName.getValue().equals("") || lastName.getValue().equals("")) {
                    ouputLabel.setText("No first- or lastname given!");
                    return;
                }

                AsyncCallback<Void> callback = new AsyncCallback<Void>() {
                    public void onFailure(Throwable caught) {
                        // TODO: Do something with errors.
                    }

                    public void onSuccess(Void result) {
                        ouputLabel.setHTML("User created!");
                    }
                };

                getService().createUser(firstName.getValue(), lastName.getValue(), callback);

                firstName.setValue(null);
                lastName.setValue(null);
            }
        });

        Button listUserBtn = new Button("Get all users!", new SelectionListener<ButtonEvent>() {
            public void componentSelected(ButtonEvent event) {
                
                AsyncCallback<List<String[]>> callback = new AsyncCallback<List<String[]>>() {
                    public void onFailure(Throwable caught) {
                        // TODO: Do something with errors.
                    }

                    public void onSuccess(List<String[]> result) {
                        ouputLabel.setHTML(null);
                        for (String[] user : result) {
                            ouputLabel.setHTML(ouputLabel.getText() + user[0] + ", " + user[1] + ", " + user[2] + " : ");
                        }
                    }
                };

                getService().getList(callback);
            }
        });

        firstName.setFieldLabel("First Name");
        lastName.setFieldLabel("Last Name");

        fp.setHeading("Create new user");
        fp.setWidth(300);
        fp.setFrame(true);
        fp.add(firstName, new FormData("100%"));
        fp.add(lastName, new FormData("100%"));
        fp.addButton(createUserBtn);
        fp.addButton(listUserBtn);

        RootPanel.get().add(fp);
        RootPanel.get().add(ouputLabel);

    }

    public static UserAsync getService() {
        return GWT.create(User.class);
    }

}
