package de.bastian.clan.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Widget;

import de.bastian.clan.client.Clan;
import de.bastian.clan.client.events.LoginEvent;
import de.bastian.clan.client.events.LogoutEvent;
import de.bastian.clan.client.forum.place.TopicPlace;
import de.bastian.clan.client.guestbook.place.GuestBookPlace;
import de.bastian.clan.client.picture.place.PicturesPlace;
import de.bastian.clan.client.place.HelloPlace;
import de.bastian.clan.client.place.LoginPlace;
import de.bastian.clan.client.user.place.UsersPlace;
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
        profile.getElement().getParentElement().addClassName(style.hidden());

        Clan.EVENTBUS.addHandler(LoginEvent.TYPE, new LoginEvent.Handler() {
            @Override
            public void onLogin(LoginEvent e) {
                login.getElement().getParentElement().addClassName(style.hidden());
                logout.getElement().getParentElement().removeClassName(style.hidden());
                profile.getElement().getParentElement().removeClassName(style.hidden());
                profile.setTargetHistoryToken("!user:" + e.getUser().getId());
            }
        });

        Clan.EVENTBUS.addHandler(LogoutEvent.TYPE, new LogoutEvent.Handler() {
            @Override
            public void onLogout(LogoutEvent e) {
                login.getElement().getParentElement().removeClassName(style.hidden());
                logout.getElement().getParentElement().addClassName(style.hidden());
                profile.getElement().getParentElement().addClassName(style.hidden());
            }
        });


        /* Locale changing */
        String locale = LocaleInfo.getCurrentLocale().getLocaleName();

        if (locale.equals("en")) {
            Anchor de = new Anchor("<img src='" + Clan.RESOURCES.flagDe().getURL() + "' />", true);
            de.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    Location.replace("/?locale=de" + Location.getHash());
                }
            });
            locales.add(de);
        } else {
            Anchor en = new Anchor("<img src='" + Clan.RESOURCES.flagEn().getURL() + "' />", true);
            en.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    Location.replace("/?locale=en" + Location.getHash());
                }
            });
            locales.add(en);
        }
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
    Anchor guestbook;

    @UiField
    Anchor members;

    @UiField
    Hyperlink profile;

    @UiField
    Anchor login;

    @UiField
    Anchor logout;

    @UiField
    FlowPanel locales;

    @UiHandler("start")
    void onStartClick(ClickEvent e) {
        Clan.PLACECONTROLLER.goTo(new HelloPlace());
    }

    @UiHandler("forum")
    void onForumClick(ClickEvent e) {
        Clan.PLACECONTROLLER.goTo(new TopicPlace());
    }

    @UiHandler("pictures")
    void onPicturesClick(ClickEvent e) {
        Clan.PLACECONTROLLER.goTo(new PicturesPlace());
    }

    @UiHandler("guestbook")
    void onGuestBookClick(ClickEvent e) {
        Clan.PLACECONTROLLER.goTo(new GuestBookPlace());
    }

    @UiHandler("members")
    void onMembersClick(ClickEvent e) {
        Clan.PLACECONTROLLER.goTo(new UsersPlace());
    }


    @UiHandler("login")
    void onLoginClick(ClickEvent e) {
        Clan.PLACECONTROLLER.goTo(new LoginPlace());
    }

    @UiHandler("logout")
    void onLogoutClick(ClickEvent e) {
        UserRequest request = Clan.REQUESTFACTORY.userRequest();
        request.logout().fire();
        Clan.EVENTBUS.fireEvent(new LogoutEvent());
    }

}
