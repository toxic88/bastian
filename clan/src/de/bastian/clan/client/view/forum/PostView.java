package de.bastian.clan.client.view.forum;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.TableCellElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.Constants;
import com.google.gwt.requestfactory.shared.Receiver;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.InlineHyperlink;
import com.google.gwt.user.client.ui.Widget;

import de.bastian.clan.client.Clan;
import de.bastian.clan.client.activity.forum.PostsActivity;
import de.bastian.clan.client.view.widgets.ConfirmPopupPanel;
import de.bastian.clan.shared.PostProxy;
import de.bastian.clan.shared.UserProxy;
import de.bastian.clan.shared.UserRequest;

public class PostView extends Composite {

    public interface Presenter {
        void removePost(PostProxy post);
    }

    public interface PostViewConstants extends Constants {
        String deletePost();
    }

    private static PostViewUiBinder uiBinder = GWT.create(PostViewUiBinder.class);

    interface PostViewUiBinder extends UiBinder<Widget, PostView> {}

    interface Style extends CssResource {
        String hidden();
    }

    public PostView(final PostProxy post, final PostsActivity activity) {
        initWidget(uiBinder.createAndBindUi(this));

        title.setInnerHTML(SafeHtmlUtils.fromString(post.getTitle()).asString());

        created.setInnerHTML(Clan.DATERENDERER.format(post.getCreated()));
        if (post.getChanged() != null) {
            changed.setInnerHTML(Clan.DATERENDERER.format(post.getChanged()));
        }

        text.setInnerHTML(SafeHtmlUtils.fromString(post.getText()).asString().replace("\n", "<br />"));

        if (Clan.CURRENTUSER == null || (post.getUser() != Clan.CURRENTUSER.getId() && !Clan.CURRENTUSER.getType().equals(UserProxy.Type.Admin))) {
            actions.addStyleName(style.hidden());
        } else {
            InlineHyperlink edit = new InlineHyperlink("<img src='" + Clan.RESOURCES.pencil().getURL() + "' />", true, "editPost:" + post.getTopic() + ":" + post.getTheme() + ":" + post.getId());
            if (post.getTheme() == null) {
                edit.setTargetHistoryToken("editTheme:" + post.getTopic() + ":" + post.getId());
            }
            actions.add(edit);

            Anchor delete = new Anchor("<img src='" + Clan.RESOURCES.delete().getURL() + "' />", true);
            delete.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    final ConfirmPopupPanel popup = ConfirmPopupPanel.get();

                    popup.setText(Clan.MESSAGES.deletePost());
                    popup.setNoHandler(null);
                    popup.setYesHandler(new ClickHandler() {
                        @Override
                        public void onClick(ClickEvent event) {
                            activity.removePost(post);
                        }
                    });

                    popup.center();
                    popup.show();
                }
            });
            actions.add(delete);
        }

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
    FlowPanel actions;

    @UiField
    TableCellElement text;

}
