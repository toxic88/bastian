package de.bastian.clan.client.forum.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.requestfactory.shared.Receiver;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.InlineHyperlink;
import com.google.gwt.user.client.ui.Widget;

import de.bastian.clan.client.Clan;
import de.bastian.clan.shared.PostProxy;
import de.bastian.clan.shared.PostRequest;
import de.bastian.clan.shared.UserProxy;
import de.bastian.clan.shared.UserRequest;

public class ThemeView extends Composite {

    private static ThemeViewUiBinder uiBinder = GWT.create(ThemeViewUiBinder.class);

    interface ThemeViewUiBinder extends UiBinder<Widget, ThemeView> {}

    public ThemeView(PostProxy theme) {
        initWidget(uiBinder.createAndBindUi(this));

        title.setHTML(SafeHtmlUtils.fromString(theme.getTitle()));
        title.setTargetHistoryToken("!posts:" + theme.getId());

        PostRequest themeRequest = Clan.REQUESTFACTORY.postRequest();
        themeRequest.countPosts(theme.getId()).fire(new Receiver<Integer>() {
           @Override
            public void onSuccess(Integer count) {
               replies.setInnerHTML("" + count);
            }
        });

        UserRequest userRequest = Clan.REQUESTFACTORY.userRequest();
        userRequest.findUser(theme.getUser()).fire(new Receiver<UserProxy>() {
            @Override
            public void onSuccess(UserProxy user) {
                username.setHTML(SafeHtmlUtils.fromString(user.getFirstname()));
                username.setTargetHistoryToken("!user:" + user.getId());
            }
        });
    }

    @UiField
    SpanElement replies;

    @UiField
    Hyperlink title;

    @UiField
    InlineHyperlink username;

}
