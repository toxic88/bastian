package de.bastian.clan.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class HelloView extends Composite {

    private static HelloViewUiBinder uiBinder = GWT.create(HelloViewUiBinder.class);

    interface HelloViewUiBinder extends UiBinder<Widget, HelloView>{}

    public HelloView() {
        initWidget(uiBinder.createAndBindUi(this));
    }

}
