package de.bastian.index.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class AppView extends Composite {

    private static AppViewUiBinder uiBinder = GWT.create(AppViewUiBinder.class);

    interface AppViewUiBinder extends UiBinder<Widget, AppView> {}

    public AppView() {
        initWidget(uiBinder.createAndBindUi(this));

        ads.appendChild(RootPanel.get("ads").getElement());
    }

    @UiField
    DivElement content;

    @UiField
    DivElement ads;

    public DivElement getContent() {
        return content;
    }

}
