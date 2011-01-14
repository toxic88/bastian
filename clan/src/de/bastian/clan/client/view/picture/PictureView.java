package de.bastian.clan.client.view.picture;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.Constants;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineHyperlink;
import com.google.gwt.user.client.ui.Widget;

import de.bastian.clan.client.Clan;
import de.bastian.clan.client.activity.picture.PicturesActivity;
import de.bastian.clan.client.mvp.AppReceiver;
import de.bastian.clan.client.view.widgets.ConfirmPopupPanel;
import de.bastian.clan.shared.PictureProxy;
import de.bastian.clan.shared.UserProxy;
import de.bastian.clan.shared.UserRequest;

public class PictureView extends Composite {

    public interface Presenter {
        void removePicture(PictureProxy picture);
    }

    public interface PictureViewConstants extends Constants {
        String deletePicture();
    }

    private static PictureViewUiBinder uiBinder = GWT.create(PictureViewUiBinder.class);

    interface PictureViewUiBinder extends UiBinder<Widget, PictureView> {}

    interface Style extends CssResource {
        String hidden();
    }

    public PictureView(final PictureProxy picture, final PicturesActivity activity) {
        initWidget(uiBinder.createAndBindUi(this));

        link.setHref(picture.getImage());
        link.setTitle(SafeHtmlUtils.fromString(picture.getDescription()).asString());
        image.setSrc(picture.getImage());
        description.setInnerHTML(SafeHtmlUtils.fromString(picture.getDescription()).asString());

        if (Clan.CURRENTUSER == null || (Clan.CURRENTUSER.getId() != picture.getUser() && !Clan.CURRENTUSER.getType().equals(UserProxy.Type.Admin))) {
            actions.addStyleName(style.hidden());
        } else {
            InlineHyperlink edit = new InlineHyperlink();
            edit.setTargetHistoryToken("editPicture:" + picture.getId());
            edit.setHTML("<img src='" + Clan.RESOURCES.pencil().getURL() + "' />");
            actions.add(edit);

            Anchor delete = new Anchor("javascript:;");
            delete.setHTML("<img src='" + Clan.RESOURCES.delete().getURL() + "' />");
            delete.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    final ConfirmPopupPanel popup = ConfirmPopupPanel.get();

                    popup.setText(Clan.MESSAGES.deletePicture());
                    popup.setNoHandler(null);
                    popup.setYesHandler(new ClickHandler() {
                        @Override
                        public void onClick(ClickEvent event) {
                            activity.removePicture(picture);
                        }
                    });

                    popup.center();
                    popup.show();
                }
            });
            actions.add(delete);
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
    FlowPanel actions;

}
