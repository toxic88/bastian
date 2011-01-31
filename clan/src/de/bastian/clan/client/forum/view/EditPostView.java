package de.bastian.clan.client.forum.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.Constants;
import com.google.gwt.requestfactory.client.RequestFactoryEditorDriver;
import com.google.gwt.requestfactory.shared.RequestContext;
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

public class EditPostView extends Composite implements Editor<PostProxy> {

    public interface EditPostViewConstants extends Constants {
        String newPost();
    }

    private static EditPostViewUiBinder uiBinder = GWT.create(EditPostViewUiBinder.class);

    interface EditPostViewUiBinder extends UiBinder<Widget, EditPostView> {}

    interface Driver extends RequestFactoryEditorDriver<PostProxy, EditPostView> {}

    private Driver driver = GWT.create(Driver.class);

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
    Button save;

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

    public void edit(TopicProxy topic, PostProxy theme, PostProxy post) {
        driver.initialize(Clan.EVENTBUS, Clan.REQUESTFACTORY, this);

        if (post == null) {
            header.setInnerHTML("<a href='#forum:'>" + Clan.MESSAGES.forum() + "</a> > <a href='#themes:" + topic.getId() + "'>" + topic.getName() + "</a> > <a href='#posts:" + theme.getId() + "'>" + theme.getTitle() + "</a> > " + Clan.MESSAGES.newPost());
        } else {
            header.setInnerHTML("<a href='#forum:'>" + Clan.MESSAGES.forum() + "</a> > <a href='#themes:" + topic.getId() + "'>" + topic.getName() + "</a> > <a href='#posts:" + theme.getId() + "'>" + theme.getTitle() + "</a> > " + post.getTitle());
        }

        PostRequest request = Clan.REQUESTFACTORY.postRequest();
        if (post == null) {
            post = request.create(PostProxy.class);
            post.setTopic(topic.getId());
            post.setTheme(theme.getId());
        }
        request.persist().using(post);

        driver.edit(post, request);
    }

}
