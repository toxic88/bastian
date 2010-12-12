package de.bastian.clan.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class ClanView extends Composite {

    private static ClanViewUiBinder uiBinder = GWT.create(ClanViewUiBinder.class);

    interface ClanViewUiBinder extends UiBinder<Widget, ClanView> {}

    public ClanView() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiField
    SimplePanel content;

    public SimplePanel getContent() {
        return content;
    }

}
