package de.bastian.clan.client.view.user;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.Constants;
import com.google.gwt.requestfactory.shared.ServerFailure;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import de.bastian.clan.client.Clan;
import de.bastian.clan.client.mvp.AppReceiver;
import de.bastian.clan.shared.UserProxy;
import de.bastian.clan.shared.UserProxy.Type;
import de.bastian.clan.shared.UserRequest;

public class EditUserView extends Composite {

    private static UserDetailViewUiBinder uiBinder = GWT.create(UserDetailViewUiBinder.class);

    interface UserDetailViewUiBinder extends UiBinder<Widget, EditUserView> {}

    public static interface EditUserConstants extends Constants {
        String createUser();
        String changeUser();
    }

    private UserProxy user = null;

    public EditUserView() {
        initWidget(uiBinder.createAndBindUi(this));

        for (UserProxy.Type t : UserProxy.Type.values()) {
            type.addItem(t.toString());
        }
    }

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
    ListBox type;

    @UiField
    Button button;

    @UiHandler("button")
    void onClickButton(ClickEvent e) {
        updateUser();
    }

    public void setUser(UserProxy user) {
        this.user = user;

        if (user == null) {
            button.setText(Clan.MESSAGES.createUser());
        } else {

            firstname.setText(user.getFirstname());
            lastname.setText(user.getLastname());
            email.setText(user.getEmail());
            steamid.setText(user.getSteamid());

            Type[] list = UserProxy.Type.values();
            for (int i = 0; i < list.length; i++) {
                if (list[i].toString().equals(user.getType())) {
                    type.setSelectedIndex(i);
                }
            }

            button.setText(Clan.MESSAGES.changeUser());
        }
    }

    private void updateUser() {
        UserRequest request = Clan.REQUESTFACTORY.userRequest();

        if (user == null) {
            user = request.create(UserProxy.class);
            user.setPassword(password.getText());
        } else {
            user = request.edit(user);
            if (!password.getText().equals("")) {
                user.setPassword(password.getText());
            }
        }
        user.setFirstname(firstname.getText());
        user.setLastname(lastname.getText());
        user.setEmail(email.getText());
        user.setSteamid(steamid.getText());
        user.setType(type.getValue(type.getSelectedIndex()));

        request.persist().using(user).fire(new AppReceiver<Void>() {
            @Override
            public void onSuccess(Void response) {
                History.back();
            }
            @Override
            public void onFailure(ServerFailure error) {
                // TODO: do something...
            }
        });
    }

    private void reset() {
        user = null;

        firstname.setText("");
        lastname.setText("");
        password.setText("");
        email.setText("");
        steamid.setText("");
        button.setText("");
    }

    @Override
    protected void onDetach() {
        super.onDetach();
        reset();
    }

}
