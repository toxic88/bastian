package de.bastian.clan.client.view.forum;

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
import de.bastian.clan.client.activity.forum.EditPostActivity;
import de.bastian.clan.shared.PostProxy;
import de.bastian.clan.shared.TopicProxy;


public class EditPostView extends Composite {

    public interface Presenter {
        void updatePost(TopicProxy topic, PostProxy theme, PostProxy post, String title, String text);
    }

    public interface EditPostViewConstants extends Constants {
        String newPost();
    }

    private static EditPostViewUiBinder uiBinder = GWT.create(EditPostViewUiBinder.class);

    interface EditPostViewUiBinder extends UiBinder<Widget, EditPostView> {}

    private EditPostActivity activity;

    private TopicProxy topic = null;
    private PostProxy theme = null;
    private PostProxy post = null;

    public EditPostView() {
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
        activity.updatePost(topic, theme, post, title.getText(), text.getText());
    }

    public void setPost(TopicProxy topic, PostProxy theme, PostProxy post) {
        this.topic = topic;
        this.theme = theme;
        this.post = post;

        if (post == null) {
            header.setInnerHTML("<a href='#forum:'>" + Clan.MESSAGES.forum() + "</a> > <a href='#themes:" + topic.getId() + "'>" + topic.getName() + "</a> > <a href='#posts:" + theme.getId() + "'>" + theme.getTitle() + "</a> > " + Clan.MESSAGES.newPost());
        } else {
            header.setInnerHTML("<a href='#forum:'>" + Clan.MESSAGES.forum() + "</a> > <a href='#themes:" + topic.getId() + "'>" + topic.getName() + "</a> > <a href='#posts:" + theme.getId() + "'>" + theme.getTitle() + "</a> > " + post.getTitle());

            title.setText(post.getTitle());
            text.setText(post.getText());
        }
    }

    private void reset() {
        topic = null;
        theme = null;
        post = null;

        title.setText("");
        text.setText("");
    }

    @Override
    protected void onDetach() {
        super.onDetach();
        reset();
    }

    public void setActivity(EditPostActivity activity) {
        this.activity = activity;
    }

}
