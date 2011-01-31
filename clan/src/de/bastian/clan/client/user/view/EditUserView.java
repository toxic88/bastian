package de.bastian.clan.client.user.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.TableRowElement;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.requestfactory.client.RequestFactoryEditorDriver;
import com.google.gwt.requestfactory.shared.RequestContext;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import de.bastian.clan.client.Clan;
import de.bastian.clan.client.mvp.AppReceiver;
import de.bastian.clan.client.view.widgets.EnumEditor;
import de.bastian.clan.shared.UserProxy;
import de.bastian.clan.shared.UserRequest;

public class EditUserView extends Composite implements Editor<UserProxy> {

    interface UserDetailViewUiBinder extends UiBinder<Widget, EditUserView> {}

    private static UserDetailViewUiBinder uiBinder = GWT.create(UserDetailViewUiBinder.class);

    interface Driver extends RequestFactoryEditorDriver<UserProxy, EditUserView> {}

    private Driver driver = GWT.create(Driver.class);

    interface Style extends CssResource {
        String hidden();
    }

    public EditUserView() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiField
    Style style;

    @UiField
    TextBox firstname;

    @UiField
    TextBox lastname;

    @UiField
    PasswordTextBox password;

    @UiField
    TextBox email;

    @UiField
    TextBox steamid;

    @UiField
    TableRowElement typerow;

    @UiField
    EnumEditor<UserProxy.Type> type;

    @UiField
    Button save;

    @UiFactory
    static EnumEditor<UserProxy.Type> getUserTypes() { // static because of the editor framework
        return new EnumEditor<UserProxy.Type>(UserProxy.Type.class);
    }

    @UiHandler("save")
    void onSaveClick(ClickEvent e) {
        RequestContext request = driver.flush();

        if (driver.hasErrors()) {
            // TODO: do something
        } else {
            request.fire(new AppReceiver<Void>() {
                @Override
                public void onSuccess(Void response) {
                    History.back();
                }
            });
        }
    }

    public void edit(UserProxy user) {
        driver.initialize(Clan.EVENTBUS, Clan.REQUESTFACTORY, this);

        typerow.removeClassName(style.hidden());
        if (!Clan.CURRENTUSER.getType().equals(UserProxy.Type.Admin)) {
            typerow.addClassName(style.hidden());
        }

        UserRequest request = Clan.REQUESTFACTORY.userRequest();
        if (user == null) {
            user = request.create(UserProxy.class);
        }
        request.persist().using(user);

        driver.edit(user, request);
    }

}
