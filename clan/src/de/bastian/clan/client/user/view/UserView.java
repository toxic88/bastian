package de.bastian.clan.client.user.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.dom.client.TableCellElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.Constants;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineHyperlink;
import com.google.gwt.user.client.ui.Widget;

import de.bastian.clan.client.Clan;
import de.bastian.clan.client.user.activity.UserActivity;
import de.bastian.clan.client.view.widgets.ConfirmPopupPanel;
import de.bastian.clan.shared.UserProxy;

public class UserView extends Composite {

    public interface Presenter {
        void removeUser(UserProxy user);
    }

    public interface UserViewConstants extends Constants {
        String user();
        String deleteUser();
    }

    private static UserViewUiBinder uiBinder = GWT.create(UserViewUiBinder.class);

    interface UserViewUiBinder extends UiBinder<Widget, UserView> {}

    interface Style extends CssResource {
        String hidden();
    }

    private UserActivity activity;

    public UserView() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiField
    Style style;

    @UiField
    HeadingElement header;

    @UiField
    FlowPanel actions;

    @UiField
    TableCellElement firstname;

    @UiField
    TableCellElement lastname;

    @UiField
    TableCellElement lastlogin;

    public void setUser(final UserProxy user) {
        header.setInnerHTML(Clan.MESSAGES.user() + ": " + SafeHtmlUtils.fromString(user.getFirstname()).asString());

        firstname.setInnerHTML(SafeHtmlUtils.fromString(user.getFirstname()).asString());
        lastname.setInnerHTML(SafeHtmlUtils.fromString(user.getLastname()).asString());
        if (user.getLastLogin() != null) {
            lastlogin.setInnerHTML(SafeHtmlUtils.fromString(Clan.DATERENDERER.format(user.getLastLogin())).asString());
        }

        if (user.getSteamid() != null && !user.getSteamid().equals("")) {
            // steaminfo
        }

        if (Clan.CURRENTUSER == null || (Clan.CURRENTUSER.getId() != user.getId() && !Clan.CURRENTUSER.getType().equals(UserProxy.Type.Admin))) {
            actions.addStyleName(style.hidden());
        } else {
            InlineHyperlink edit = new InlineHyperlink("<img src='" + Clan.RESOURCES.pencil().getURL() + "' />", true, "!editUser:" + user.getId());
            actions.add(edit);

            Anchor delete = new Anchor("<img src='" + Clan.RESOURCES.delete().getURL() + "' />", true);
            delete.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    final ConfirmPopupPanel popup = ConfirmPopupPanel.get();

                    popup.setText(Clan.MESSAGES.deleteUser());
                    popup.setNoHandler(null);
                    popup.setYesHandler(new ClickHandler() {
                        @Override
                        public void onClick(ClickEvent event) {
                            activity.removeUser(user);
                        }
                    });

                    popup.center();
                    popup.show();
                }
            });
            actions.add(delete);
        }
    }

    private void reset() {
        actions.clear();
        actions.removeStyleName(style.hidden());
        firstname.setInnerHTML("");
        lastname.setInnerHTML("");
        lastlogin.setInnerHTML("");
    }

    @Override
    protected void onDetach() {
        super.onDetach();
        reset();
    }

    public void setActivity(UserActivity activity) {
        this.activity = activity;
    }

}
