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
import de.bastian.clan.shared.UserProxy;


public class EditPostView extends Composite {

    private static EditPostViewUiBinder uiBinder = GWT.create(EditPostViewUiBinder.class);

    interface EditPostViewUiBinder extends UiBinder<Widget, EditPostView> {}

    public static interface EditPostViewConstants extends Constants {
        String newPost();
    }

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
        updatePost();
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

    private void updatePost() {
        if (Clan.CURRENTUSER == null || (post != null && (post.getUser() != Clan.CURRENTUSER.getId() && !Clan.CURRENTUSER.getType().equals(UserProxy.Type.Admin)))) {
            History.back();
            return;
        }

        PostRequest request = Clan.REQUESTFACTORY.postRequest();

        if (post == null) {
            post = request.create(PostProxy.class);
            post.setTopic(topic.getId());
            post.setTheme(theme.getId());
            post.setUser(Clan.CURRENTUSER.getId());
        } else {
            post = request.edit(post);
        }
        post.setTitle(title.getText());
        post.setText(text.getText());

        request.persist().using(post).fire(new AppReceiver<Void>() {
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
        post = null;

        title.setText("");
        text.setText("");
    }

    @Override
    protected void onDetach() {
        super.onDetach();
        reset();
    }

}
