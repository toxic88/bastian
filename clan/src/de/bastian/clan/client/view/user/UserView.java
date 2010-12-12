package de.bastian.clan.client.view.user;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.dom.client.TableCellElement;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import de.bastian.clan.client.Clan;
import de.bastian.clan.shared.UserProxy;


public class UserView extends Composite {

    private static UserViewUiBinder uiBinder = GWT.create(UserViewUiBinder.class);

    interface UserViewUiBinder extends UiBinder<Widget, UserView> {}

    public UserView() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiField
    HeadingElement header;

    @UiField
    TableCellElement firstname;

    @UiField
    TableCellElement lastname;

    @UiField
    TableCellElement lastlogin;

    public void setUser(UserProxy user) {
        header.setInnerHTML("User: " + SafeHtmlUtils.fromString(user.getFirstname()).asString());

        firstname.setInnerHTML(SafeHtmlUtils.fromString(user.getFirstname()).asString());
        lastname.setInnerHTML(SafeHtmlUtils.fromString(user.getLastname()).asString());
        if (user.getLastLogin() != null) {
            lastlogin.setInnerHTML(SafeHtmlUtils.fromString(Clan.DATERENDERER.format(user.getLastLogin())).asString());
        }

        if (user.getSteamid() != null && !user.getSteamid().equals("")) {
            // steaminfo
        }
    }

    private void reset() {
        firstname.setInnerHTML("");
        lastname.setInnerHTML("");
        lastlogin.setInnerHTML("");
    }

    @Override
    protected void onDetach() {
        super.onDetach();
        reset();
    }

}
