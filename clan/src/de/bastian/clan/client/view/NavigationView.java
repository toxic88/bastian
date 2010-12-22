package de.bastian.clan.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import de.bastian.clan.client.Clan;
import de.bastian.clan.client.events.LoginEvent;
import de.bastian.clan.client.events.LogoutEvent;
import de.bastian.clan.client.place.HelloPlace;
import de.bastian.clan.client.place.LoginPlace;
import de.bastian.clan.client.place.forum.TopicPlace;
import de.bastian.clan.client.place.picture.PicturesPlace;
import de.bastian.clan.client.place.user.UsersPlace;
import de.bastian.clan.shared.UserRequest;

public class NavigationView extends Composite {

    private static NavigationViewUiBinder uiBinder = GWT.create(NavigationViewUiBinder.class);

    interface NavigationViewUiBinder extends UiBinder<Widget, NavigationView> {}

    interface Style extends CssResource {
        String hidden();
    }

    public NavigationView() {
        initWidget(uiBinder.createAndBindUi(this));

        logout.getElement().getParentElement().addClassName(style.hidden());

        Clan.EVENTBUS.addHandler(LoginEvent.TYPE, new LoginEvent.Handler() {
            @Override
            public void onLogin(LoginEvent e) {
                login.getElement().getParentElement().addClassName(style.hidden());
                logout.getElement().getParentElement().removeClassName(style.hidden());
            }
        });

        Clan.EVENTBUS.addHandler(LogoutEvent.TYPE, new LogoutEvent.Handler() {
            @Override
            public void onLogout(LogoutEvent e) {
                login.getElement().getParentElement().removeClassName(style.hidden());
                logout.getElement().getParentElement().addClassName(style.hidden());
            }
        });
    }

    @UiField
    Style style;

    @UiField
    Anchor start;

    @UiField
    Anchor forum;

    @UiField
    Anchor pictures;

    @UiField
    Anchor members;

    @UiField
    Anchor login;

    @UiField
    Anchor logout;

    @UiHandler("start")
    void onClickStart(ClickEvent e) {
        Clan.PLACECONTROLLER.goTo(new HelloPlace());
    }

    @UiHandler("forum")
    void onClickForum(ClickEvent e) {
        Clan.PLACECONTROLLER.goTo(new TopicPlace());
    }

    @UiHandler("pictures")
    void onClickPictures(ClickEvent e) {
        Clan.PLACECONTROLLER.goTo(new PicturesPlace());
    }

    @UiHandler("members")
    void onClickMembers(ClickEvent e) {
        Clan.PLACECONTROLLER.goTo(new UsersPlace());
    }


    @UiHandler("login")
    void onClickLogin(ClickEvent e) {
        Clan.PLACECONTROLLER.goTo(new LoginPlace());
    }

    @UiHandler("logout")
    void onClickLogout(ClickEvent e) {
        UserRequest request = Clan.REQUESTFACTORY.userRequest();
        request.logout().fire();
        Clan.EVENTBUS.fireEvent(new LogoutEvent());
    }

}
