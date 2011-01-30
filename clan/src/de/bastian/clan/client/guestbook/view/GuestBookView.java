package de.bastian.clan.client.guestbook.view;

import java.util.List;

import com.google.gwt.appengine.channel.client.Channel;
import com.google.gwt.appengine.channel.client.ChannelFactory;
import com.google.gwt.appengine.channel.client.CreateChannelCallback;
import com.google.gwt.appengine.channel.client.SocketListener;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.requestfactory.shared.ServerFailure;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import de.bastian.clan.client.Clan;
import de.bastian.clan.client.events.LoginEvent;
import de.bastian.clan.client.mvp.AppReceiver;
import de.bastian.clan.shared.GuestBookEntry;
import de.bastian.clan.shared.GuestBookEntryProxy;
import de.bastian.clan.shared.GuestBookEntryRequest;
import de.bastian.clan.shared.PushClientProxy;
import de.bastian.clan.shared.PushClientRequest;

public class GuestBookView extends Composite {

    private static GuestBookViewUiBinder uiBinder = GWT.create(GuestBookViewUiBinder.class);

    interface GuestBookViewUiBinder extends UiBinder<Widget, GuestBookView> {}

    interface Style extends CssResource {
        String hidden();
    }

    public GuestBookView() {
        initWidget(uiBinder.createAndBindUi(this));

        onLogout();

        Scheduler.get().scheduleDeferred(new ScheduledCommand() {
            @Override
            public void execute() {
                Clan.EVENTBUS.addHandler(LoginEvent.TYPE, new LoginEvent.Handler() {
                    @Override
                    public void onLogin(LoginEvent e) {
                        initChannel(e.getUser().getFirstname());
                        name.setText(e.getUser().getFirstname());
                    }
                });
            }
        });
    }

    @UiField
    Style style;

    @UiField
    TextBox name;

    @UiField
    TextArea text;

    @UiField
    Button login;

    @UiField
    Button logout;

    @UiField
    Button send;

    @UiField
    FlowPanel content;

    @UiHandler("text")
    void onTextKeyDown(KeyDownEvent e) {
        if (e.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
            send.click();
        }
    }

    @UiHandler("login")
    void onLoginClick(ClickEvent e) {
        if (Clan.SOCKET == null) {
            initChannel(name.getText());
        }
    }

    @UiHandler("logout")
    void onLogoutClick(ClickEvent e) {
        if (Clan.SOCKET != null) {
            Clan.SOCKET.close();
        }
    }

    @UiHandler("send")
    void onSendClick(ClickEvent e) {
        if (Clan.SOCKET != null) {
            GuestBookEntryRequest request = Clan.REQUESTFACTORY.guestBookEntryRequest();

            GuestBookEntryProxy guestBookEntry = request.create(GuestBookEntryProxy.class);
            guestBookEntry.setName(name.getText());
            guestBookEntry.setText(text.getText());

            request.persist().using(guestBookEntry).fire();

            text.setText(null);
        }
    }

    private void initChannel(String name) {
        if (Clan.SOCKET != null || name == null || name.trim().isEmpty()) {
            return;
        }

        PushClientRequest request = Clan.REQUESTFACTORY.pushClientRequest();
        request.create(name).fire(new AppReceiver<PushClientProxy>() {
            @Override
            public void onSuccess(final PushClientProxy pushClient) {
                if (pushClient == null) {
                    Window.alert("error no pushclient created!");
                    return;
                }
                ChannelFactory.createChannel(pushClient.getChannel(), new CreateChannelCallback() {
                    @Override
                    public void onCreateChannel(Channel ch) {
                        ch.open(new SocketListener() {
                            @Override
                            public void onOpen() {
                                Clan.SOCKET = getSocket();

                                onLogin();
                            }
                            @Override
                            public void onMessage(String message) {
                                GuestBookEntry entry = (GuestBookEntry) JsonUtils.safeEval(message);
                                content.insert(new GuestBookEntryView(entry), 0);
                            }
                            @Override
                            public void onClose() {
                                Clan.SOCKET = null;

                                onLogout();

                                PushClientRequest request = Clan.REQUESTFACTORY.pushClientRequest();
                                request.remove().using(pushClient).fire();
                            }
                        });
                    }
                });
            }
            @Override
            public void onFailure(ServerFailure error) {
                // TODO: do something
            }
        });
    }

    private void onLogout() {
        name.setEnabled(true);
        login.removeStyleName(style.hidden());
        logout.addStyleName(style.hidden());
        text.getElement().getParentElement().getParentElement().addClassName(style.hidden());
        send.addStyleName(style.hidden());
    }

    private void onLogin() {
        name.setEnabled(false);
        login.addStyleName(style.hidden());
        logout.removeStyleName(style.hidden());
        text.getElement().getParentElement().getParentElement().removeClassName(style.hidden());
        send.removeStyleName(style.hidden());
    }

    public void setGuestBookEntries(List<GuestBookEntryProxy> guestBookEntries) {
        content.clear();
        for (GuestBookEntryProxy guestBookEntry : guestBookEntries) {
            content.add(new GuestBookEntryView(guestBookEntry));
        }
    }

}
