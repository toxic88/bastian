package de.bastian.clan.client.view.forum;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.TableCellElement;
import com.google.gwt.requestfactory.shared.Receiver;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Widget;

import de.bastian.clan.client.Clan;
import de.bastian.clan.shared.PostProxy;
import de.bastian.clan.shared.UserProxy;
import de.bastian.clan.shared.UserRequest;

public class PostView extends Composite {

    private static PostViewUiBinder uiBinder = GWT.create(PostViewUiBinder.class);

    interface PostViewUiBinder extends UiBinder<Widget, PostView> {}

    interface Style extends CssResource {
        String hidden();
    }

    public PostView(final PostProxy post) {
        initWidget(uiBinder.createAndBindUi(this));

        title.setInnerHTML(SafeHtmlUtils.fromString(post.getTitle()).asString());

        created.setInnerHTML(Clan.DATERENDERER.format(post.getCreated()));
        if (post.getChanged() != null) {
            changed.setInnerHTML(Clan.DATERENDERER.format(post.getChanged()));
        }

        if (Clan.CURRENTUSER == null || (post.getUser() != Clan.CURRENTUSER.getId() && !Clan.CURRENTUSER.getType().equals(UserProxy.Type.Admin))) {
            links.addClassName(style.hidden());
        } else {
            if (post.getTheme() == null) {
                links.setInnerHTML("<a href='#editTheme:" + post.getTopic() + ":" + post.getId() + "'><img src='" + Clan.RESOURCES.pencil().getURL() + "' /></a>");
            } else {
                links.setInnerHTML("<a href='#editPost:" + post.getTopic() + ":" + post.getTheme() + ":" + post.getId() + "'><img src='" + Clan.RESOURCES.pencil().getURL() + "' /></a>");
            }
        }

        text.setInnerHTML(SafeHtmlUtils.fromString(post.getText()).asString().replace("\n", "<br />"));

        UserRequest request = Clan.REQUESTFACTORY.userRequest();
        request.findUser(post.getUser()).fire(new Receiver<UserProxy>() {
           @Override
            public void onSuccess(UserProxy user) {
               username.setHTML(SafeHtmlUtils.fromString(user.getFirstname()));
               username.setTargetHistoryToken("user:" + user.getId());
            }
        });
    }

    @UiField
    Style style;

    @UiField
    Hyperlink username;

    @UiField
    DivElement created;

    @UiField
    DivElement changed;

    @UiField
    DivElement title;

    @UiField
    DivElement links;

    @UiField
    TableCellElement text;

}
