package de.bastian.clan.client.view.user;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.TableRowElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import de.bastian.clan.client.Clan;
import de.bastian.clan.client.activity.user.EditUserActivity;
import de.bastian.clan.shared.UserProxy;
import de.bastian.clan.shared.UserProxy.Type;

public class EditUserView extends Composite {

    public interface Presenter {
        void updateUser(UserProxy user, String firstname, String lastname, String email, String password, String steamid, Type type);
    }

    private static UserDetailViewUiBinder uiBinder = GWT.create(UserDetailViewUiBinder.class);

    interface UserDetailViewUiBinder extends UiBinder<Widget, EditUserView> {}

    interface Style extends CssResource {
        String hidden();
    }

    private EditUserActivity activity;

    private UserProxy user = null;

    public EditUserView() {
        initWidget(uiBinder.createAndBindUi(this));

        for (UserProxy.Type t : UserProxy.Type.values()) {
            type.addItem(t.toString());
        }
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
    ListBox type;

    @UiField
    Button button;

    @UiHandler("button")
    void onClickButton(ClickEvent e) {
        activity.updateUser(user, firstname.getText(), lastname.getText(), email.getText(), password.getText(), steamid.getText(), Type.valueOf(type.getValue(type.getSelectedIndex())));
    }

    public void setUser(UserProxy user) {
        this.user = user;

        if (user != null) {
            firstname.setText(user.getFirstname());
            lastname.setText(user.getLastname());
            email.setText(user.getEmail());
            steamid.setText(user.getSteamid());

            Type[] list = UserProxy.Type.values();
            for (int i = 0; i < list.length; i++) {
                if (list[i].equals(user.getType())) {
                    type.setSelectedIndex(i);
                }
            }

            if (!Clan.CURRENTUSER.getType().equals(UserProxy.Type.Admin)) {
                typerow.addClassName(style.hidden());
            }
        }
    }

    private void reset() {
        user = null;
        firstname.setText("");
        lastname.setText("");
        password.setText("");
        email.setText("");
        steamid.setText("");
    }

    @Override
    protected void onDetach() {
        super.onDetach();
        reset();
    }

    public void setActivity(EditUserActivity activity) {
        this.activity = activity;
    }

}
