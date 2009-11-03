package de.bastian.client.view;

import com.google.gwt.user.client.ui.RootPanel;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.HtmlContainer;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import de.bastian.client.AppEvents;
import de.bastian.client.widget.UserGrid;
import de.bastian.client.Application;
import de.bastian.client.UserManagerAsync;

public class AppView extends View {

    public static final String CENTER = "center";
    public static final String WEST = "west";

    private Viewport viewport;
    private LayoutContainer center;
    private LayoutContainer west;

    public AppView(Controller controller) {
        super(controller);
    }

    private void initUI() {
        this.viewport = new Viewport();
        this.viewport.setLayout(new BorderLayout());

        this.createNorth();
        this.createCenter();
        this.createWest();

        Registry.register(AppView.CENTER, this.center);
        Registry.register(AppView.WEST, this.west);

        RootPanel.get().add(this.viewport);
    }

    private void createNorth() {
        HtmlContainer northPanel = new HtmlContainer("<h1 class='header'>Bastian's Homepage</h1>");

        BorderLayoutData data = new BorderLayoutData(LayoutRegion.NORTH, 33);
        data.setMargins(new Margins());
        this.viewport.add(northPanel, data);
    }

    private void createCenter() {
        this.center = new LayoutContainer(new FitLayout());

        ContentPanel p = new ContentPanel(new FitLayout());
        p.add(UserGrid.get());
        p.setHeading("Users");
        p.setFrame(true);

        ToolBar toolBar = new ToolBar();
        toolBar.setSpacing(2);
        final TextField<String> username = new TextField<String>();
        toolBar.add(username);
        final TextField<String> password = new TextField<String>();
        password.setPassword(true);
        toolBar.add(password);
        toolBar.add(new Button("Add", new SelectionListener<ButtonEvent>() {
            public void componentSelected(ButtonEvent event) {

                if (username.getValue() == null || password.getValue() == null) {
                    return;
                }

                AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {
                    public void onFailure(Throwable caught) {
                        Window.alert(caught.toString());
                    }

                    public void onSuccess(Boolean result) {
                        if (!result) {
                            username.setValue(null);
                            password.setValue(null);
                            Window.alert("User allready exists!");
                        } else {
                            UserGrid.get().getStore().getLoader().load();
                        }
                    }
                };

                UserManagerAsync userService = (UserManagerAsync) Registry.get(Application.Services.User);
                userService.createUser(username.getValue(), password.getValue(), callback);

                username.setValue(null);
                password.setValue(null);
            }
        }));
        p.setTopComponent(toolBar);

        this.center.add(p);

        BorderLayoutData data = new BorderLayoutData(LayoutRegion.CENTER);
        data.setMargins(new Margins(5, 5, 5, 5));

        this.viewport.add(this.center, data);
    }

    private void createWest() {
        this.west = new LayoutContainer(new FitLayout());

        ContentPanel p = new ContentPanel();
        p.addText("<h1 class='header'>Bastian's Homepage</h1>");
        p.setHeading("Willkommen");
        p.setFrame(true);
        this.west.add(p);

        BorderLayoutData data = new BorderLayoutData(LayoutRegion.WEST);
        data.setMargins(new Margins(5, 0, 5, 5));

        this.viewport.add(this.west, data);
    }
    
    @Override
    protected void handleEvent(AppEvent event) {
        if (event.getType() == AppEvents.Init) {
            this.initUI();
        }
    }

}
