package de.bastian.clan.client.view.forum;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.Constants;
import com.google.gwt.requestfactory.shared.ServerFailure;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import de.bastian.clan.client.Clan;
import de.bastian.clan.client.mvp.AppReceiver;
import de.bastian.clan.shared.PostProxy;
import de.bastian.clan.shared.PostRequest;
import de.bastian.clan.shared.TopicProxy;


public class EditThemeView extends Composite {

    private static EditThemeViewUiBinder uiBinder = GWT.create(EditThemeViewUiBinder.class);

    interface EditThemeViewUiBinder extends UiBinder<Widget, EditThemeView> {}

    private TopicProxy topic = null;
    private PostProxy theme = null;

    public static interface EditThemeViewConstants extends Constants {
        String newTheme();
        String createTheme();
        String changeTheme();
    }

    public EditThemeView() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiField
    HeadingElement header;

    @UiField
    TextBox title;

    @UiField
    TextArea text;

    @UiField
    Button button;

    @UiHandler("button")
    void onClickButton(ClickEvent e) {
        updateTheme();
    }

    public void setTheme(TopicProxy topic, PostProxy theme) {
        this.topic = topic;
        this.theme = theme;

        if (theme == null) {
            header.setInnerHTML("<a href='#forum:'>" + Clan.MESSAGES.forum() + "</a> > <a href='#themes:" + topic.getId() + "'>" + topic.getName() + "</a> > " + Clan.MESSAGES.newTheme());
            button.setText(Clan.MESSAGES.createTheme());
        } else {
            header.setInnerHTML("<a href='#forum:'>" + Clan.MESSAGES.forum() + "</a> > <a href='#themes:" + topic.getId() + "'>" + topic.getName() + "</a> > " + theme.getTitle());

            title.setText(theme.getTitle());
            text.setText(theme.getText());
            button.setText(Clan.MESSAGES.changeTheme());
        }
    }

    private void updateTheme() {
        if (Clan.CURRENTUSER == null) {
            History.back();
            return;
        }

        PostRequest request = Clan.REQUESTFACTORY.postRequest();

        if (theme == null) {
            theme = request.create(PostProxy.class);
            theme.setTopic(topic.getId());
            theme.setUser(Clan.CURRENTUSER.getId());
        } else {
            if (theme.getUser() != Clan.CURRENTUSER.getId()) {
                History.back();
                return;
            }

            theme = request.edit(theme);
        }
        theme.setTitle(title.getText());
        theme.setText(text.getText());

        request.persist().using(theme).fire(new AppReceiver<Void>() {
            @Override
            public void onSuccess(Void response) {
                History.back();
            }
            @Override
            public void onFailure(ServerFailure error) {
                // TODO: do something....
            }
        });
    }

    private void reset() {
        topic = null;
        theme = null;

        title.setText("");
        text.setText("");
        button.setText("");
    }

    @Override
    protected void onDetach() {
        super.onDetach();
        reset();
    }

}
