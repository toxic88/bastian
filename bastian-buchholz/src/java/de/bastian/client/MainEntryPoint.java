package de.bastian.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class MainEntryPoint implements EntryPoint {

    public void onModuleLoad() {

        final Label resultLbl = new Label();

        Button btn = new Button("Get all users!", new ClickHandler() {
            public void onClick(ClickEvent event) {
                
                AsyncCallback<String> callback = new AsyncCallback<String>() {
                    public void onFailure(Throwable caught) {
                        // TODO: Do something with errors.
                    }

                    public void onSuccess(String result) {
                        resultLbl.setText(result);
                    }
                };

                getService().myMethod(callback);
                
            }
        });

        // Associate the Main panel with the HTML host page.
        RootPanel.get().add(btn);
        RootPanel.get().add(resultLbl);

    }

    public static UserAsync getService() {
        return GWT.create(User.class);
    }

}
