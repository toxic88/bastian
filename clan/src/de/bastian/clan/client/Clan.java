package de.bastian.clan.client;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.appengine.channel.client.Socket;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.requestfactory.shared.Receiver;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

import de.bastian.clan.client.events.LoginEvent;
import de.bastian.clan.client.events.LogoutEvent;
import de.bastian.clan.client.message.AppMessages;
import de.bastian.clan.client.mvp.AppActivityMapper;
import de.bastian.clan.client.mvp.AppPlaceHistoryMapper;
import de.bastian.clan.client.place.HelloPlace;
import de.bastian.clan.client.place.LoginPlace;
import de.bastian.clan.client.resource.Resources;
import de.bastian.clan.client.view.ClanView;
import de.bastian.clan.shared.AppRequestFactory;
import de.bastian.clan.shared.UserProxy;

public class Clan implements EntryPoint {

    /**
     * Static images, css files, other ressources and translations
     */
    public static final Resources RESOURCES = GWT.create(Resources.class);
    public static final AppMessages MESSAGES = GWT.create(AppMessages.class);

    /**
     * Create the RequestFactory and the MVA framework
     */
    public static final AppRequestFactory REQUESTFACTORY = GWT.create(AppRequestFactory.class);
    public static final ClientFactory CLIENTFACTORY = GWT.create(ClientFactory.class);
    public static final EventBus EVENTBUS = CLIENTFACTORY.getEventBus();
    public static final PlaceController PLACECONTROLLER = CLIENTFACTORY.getPlaceController();

    /**
     * The global DateTimeFormatter
     */
    public static final DateTimeFormat DATERENDERER = DateTimeFormat.getFormat("dd.MM.yyyy HH:mm:ss");

    /**
     * Stores the current logged in user
     */
    public static UserProxy CURRENTUSER = null;
    /**
     * Stores the socket connection for push notifications
     */
    public static Socket SOCKET = null;

    /**
     * Regulare expressions
     */
    public static enum REGEXP {
        LINEBREAK(RegExp.compile("\n", "gi"), " <br /> "),
        HYPERLINK(RegExp.compile("((?:https?|ftps?)\\:\\/\\/.+?)(?:$|\\s)", "gi"), " <a href='$1' target='_blank'>$1</a> ");

        private RegExp regexp;
        private String replace;

        REGEXP(RegExp regexp, String replace) {
            this.regexp = regexp;
            this.replace = replace;
        }
        public RegExp getRegExp() {
            return regexp;
        }
        public String getReplace() {
            return replace;
        }
    }

    private final Place defaultPlace = new HelloPlace();
    private final ClanView appWidget = new ClanView();

    @Override
    public void onModuleLoad() {
        /**
         * inject css files
         */
        RESOURCES.style().ensureInjected();
        RESOURCES.shadowbox().ensureInjected();

        /**
         * Initialize the mva framework
         */
        ActivityMapper activityMapper = new AppActivityMapper(CLIENTFACTORY);
        ActivityManager activityManager = new ActivityManager(activityMapper, EVENTBUS);
        activityManager.setDisplay(appWidget.getContent());

        AppPlaceHistoryMapper historyMapper = GWT.create(AppPlaceHistoryMapper.class);
        final PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
        historyHandler.register(PLACECONTROLLER, EVENTBUS, defaultPlace);

        RootPanel.get().add(appWidget);

        REQUESTFACTORY.initialize(EVENTBUS);

        /**
         * Stores the user who has been logged in currently
         */
        EVENTBUS.addHandler(LoginEvent.TYPE, new LoginEvent.Handler() {
            @Override
            public void onLogin(LoginEvent e) {
                if (e.getUser() != null) {
                    CURRENTUSER = e.getUser();
                }
            }
        });

        /**
         * Set the current user to null if he logs out
         */
        EVENTBUS.addHandler(LogoutEvent.TYPE, new LogoutEvent.Handler() {
            @Override
            public void onLogout(LogoutEvent e) {
                CURRENTUSER = null;
                if (SOCKET != null) {
                    SOCKET.close();
                }
            }
        });

        /**
         * Try to close the socket connection on window close
         */
        Window.addCloseHandler(new CloseHandler<Window>() {
            @Override
            public void onClose(CloseEvent<Window> event) {
                if (SOCKET != null) {
                    SOCKET.close();
                }
            }
        });

        /**
         * Check if user is allready logged in on startup
         */
        REQUESTFACTORY.userRequest().getCurrentUser().fire(new Receiver<UserProxy>() {
            @Override
            public void onSuccess(UserProxy user) {
                if (user != null){
                    EVENTBUS.fireEvent(new LoginEvent(user));
                    if (PLACECONTROLLER.getWhere() instanceof LoginPlace) {
                        History.back(); // go back where you came from before you hit the login place
                    }
                }
            }
        });

        historyHandler.handleCurrentHistory();

        /**
         * Init the shadowbox
         */
        initShadowbox();
    }

    /**
     * Initialize the shadowbox with some default options
     * @return void
     */
    private final native void initShadowbox() /*-{
        $wnd.Shadowbox.init({
            continuous  : true,
            counterType : "skip",
            skipSetup   : true
        });
    }-*/;

}
