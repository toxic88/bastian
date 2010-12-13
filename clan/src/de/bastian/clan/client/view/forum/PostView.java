package de.bastian.clan.client.view.forum;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.dom.client.TableCellElement;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import de.bastian.clan.client.Clan;
import de.bastian.clan.shared.PostProxy;
import de.bastian.clan.shared.UserProxy;

public class PostView extends Composite {

    private static PostViewUiBinder uiBinder = GWT.create(PostViewUiBinder.class);

    interface PostViewUiBinder extends UiBinder<Widget, PostView> {}

    public PostView(PostProxy post, UserProxy user) {
        initWidget(uiBinder.createAndBindUi(this));

        title.setInnerHTML(SafeHtmlUtils.fromString(post.getTitle()).asString());

        if (post.getPost() == null) {
            links.setInnerHTML("<a href='#editTheme:" + post.getTopic() + ":" + post.getId() + "'><img src='" + Clan.RESOURCES.pencil().getURL() + "' /></a>");
        } else {
            links.setInnerHTML("<a href='#editPost:" + post.getTopic() + ":" + post.getPost() + ":" + post.getId() + "'><img src='" + Clan.RESOURCES.pencil().getURL() + "' /></a>");
        }

        text.setInnerHTML(SafeHtmlUtils.fromString(post.getText()).asString());
    }

    @UiField
    TableCellElement user;

    @UiField
    SpanElement title;

    @UiField
    SpanElement links;

    @UiField
    TableCellElement text;

}
