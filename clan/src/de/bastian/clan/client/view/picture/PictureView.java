package de.bastian.clan.client.view.picture;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineHyperlink;
import com.google.gwt.user.client.ui.Widget;

import de.bastian.clan.client.Clan;
import de.bastian.clan.client.mvp.AppReceiver;
import de.bastian.clan.shared.PictureProxy;
import de.bastian.clan.shared.UserProxy;
import de.bastian.clan.shared.UserRequest;

public class PictureView extends Composite {

    private static PictureViewUiBinder uiBinder = GWT.create(PictureViewUiBinder.class);

    interface PictureViewUiBinder extends UiBinder<Widget, PictureView> {}

    interface Style extends CssResource {
        String hidden();
    }

    public PictureView(PictureProxy picture) {
        initWidget(uiBinder.createAndBindUi(this));

        link.setHref(picture.getImage());
        image.setSrc(picture.getImage());
        description.setInnerHTML(SafeHtmlUtils.fromString(picture.getDescription()).asString());

        if (Clan.CURRENTUSER == null || (Clan.CURRENTUSER.getId() != picture.getUser() && !Clan.CURRENTUSER.getType().equals(UserProxy.Type.Admin))) {
            actions.addClassName(style.hidden());
        } else {
            actions.setInnerHTML("<a href='#editPicture:" + picture.getId() + "'><img src='" + Clan.RESOURCES.pencil().getURL() + "' /></a>");
        }

        UserRequest request = Clan.REQUESTFACTORY.userRequest();
        request.findUser(picture.getUser()).fire(new AppReceiver<UserProxy>() {
            @Override
            public void onSuccess(UserProxy user) {
                username.setHTML(SafeHtmlUtils.fromString(user.getFirstname()).asString());
                username.setTargetHistoryToken("user:" + user.getId());
            }
        });

    }

    @UiField
    Style style;

    @UiField
    AnchorElement link;

    @UiField
    ImageElement image;

    @UiField
    DivElement description;

    @UiField
    InlineHyperlink username;

    @UiField
    DivElement actions;

}
