package de.bastian.clan.client.forum.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.Constants;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import de.bastian.clan.client.Clan;
import de.bastian.clan.client.forum.activity.EditThemeActivity;
import de.bastian.clan.shared.PostProxy;
import de.bastian.clan.shared.TopicProxy;

public class EditThemeView extends Composite {

    public interface Presenter {
        void updateTheme(TopicProxy topic, PostProxy theme, String title, String text);
    }

    public interface EditThemeViewConstants extends Constants {
        String newTheme();
    }

    private static EditThemeViewUiBinder uiBinder = GWT.create(EditThemeViewUiBinder.class);

    interface EditThemeViewUiBinder extends UiBinder<Widget, EditThemeView> {}

    private EditThemeActivity activity;

    private TopicProxy topic = null;
    private PostProxy theme = null;

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
        activity.updateTheme(topic, theme, title.getText(), text.getText());
    }

    public void setTheme(TopicProxy topic, PostProxy theme) {
        this.topic = topic;
        this.theme = theme;

        if (theme == null) {
            header.setInnerHTML("<a href='#forum:'>" + Clan.MESSAGES.forum() + "</a> > <a href='#themes:" + topic.getId() + "'>" + topic.getName() + "</a> > " + Clan.MESSAGES.newTheme());
        } else {
            header.setInnerHTML("<a href='#forum:'>" + Clan.MESSAGES.forum() + "</a> > <a href='#themes:" + topic.getId() + "'>" + topic.getName() + "</a> > " + theme.getTitle());

            title.setText(theme.getTitle());
            text.setText(theme.getText());
        }
    }

    private void reset() {
        topic = null;
        theme = null;
        title.setText("");
        text.setText("");
    }

    @Override
    protected void onDetach() {
        super.onDetach();
        reset();
    }

    public void setActivity(EditThemeActivity activity) {
        this.activity = activity;
    }

}
