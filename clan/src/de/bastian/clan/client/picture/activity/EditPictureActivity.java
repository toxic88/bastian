package de.bastian.clan.client.picture.activity;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.requestfactory.shared.ServerFailure;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import de.bastian.clan.client.Clan;
import de.bastian.clan.client.ClientFactory;
import de.bastian.clan.client.mvp.AppActivity;
import de.bastian.clan.client.mvp.AppReceiver;
import de.bastian.clan.client.picture.place.EditPicturePlace;
import de.bastian.clan.client.picture.view.EditPictureView;
import de.bastian.clan.shared.PictureProxy;
import de.bastian.clan.shared.PictureRequest;
import de.bastian.clan.shared.UserProxy;
import de.bastian.gwt.fileapi.client.file.FileReader;

public class EditPictureActivity extends AppActivity implements EditPictureView.Presenter {

    private Long pictureId;

    public EditPictureActivity(EditPicturePlace place, ClientFactory clientFactory) {
        super(place, clientFactory);
        pictureId = place.getPictureId();
    }

    @Override
    public void start(final AcceptsOneWidget containerWidget, EventBus eventBus) {
        final EditPictureView editPictureView = clientFactory.getEditPictureView();
        editPictureView.setActivity(this);

        if (pictureId != null && FileReader.isSupported()) {
            PictureRequest request = Clan.REQUESTFACTORY.pictureRequest();

            request.findPicture(pictureId).fire(new AppReceiver<PictureProxy>() {
                @Override
                public void onSuccess(PictureProxy picture) {
                    if (Clan.CURRENTUSER == null || (picture != null && (picture.getUser() != Clan.CURRENTUSER.getId() && !Clan.CURRENTUSER.getType().equals(UserProxy.Type.Admin)))) {
                        History.back();
                        return;
                    }

                    editPictureView.setPicture(picture);
                    containerWidget.setWidget(editPictureView);
                }
            });
        } else if (Clan.CURRENTUSER != null && FileReader.isSupported()) {
            editPictureView.setPicture(null);
            containerWidget.setWidget(editPictureView);
        } else {
            Scheduler.get().scheduleDeferred(new ScheduledCommand() {
                @Override
                public void execute() {
                    if (!FileReader.isSupported()) {
                        // TODO: show error
                    }
                    History.back();
                }
            });
        }
    }

    @Override
    public void updatePicture(PictureProxy picture, String image, String description) {
        if (Clan.CURRENTUSER == null || (picture != null && (picture.getUser() != Clan.CURRENTUSER.getId() && !Clan.CURRENTUSER.getType().equals(UserProxy.Type.Admin)))) {
            History.back();
            return;
        }

        if (image.isEmpty() || description.isEmpty()) {
            // TODO: show input errors
            return;
        }

        PictureRequest request = Clan.REQUESTFACTORY.pictureRequest();

        if (picture == null) {
            picture = request.create(PictureProxy.class);
            picture.setUser(Clan.CURRENTUSER.getId());
        } else {
            picture = request.edit(picture);
        }
        picture.setDescription(description);
        picture.setImage(image);

        request.persist().using(picture).fire(new AppReceiver<Void>() {
            @Override
            public void onSuccess(Void response) {
                History.back();
            }
            @Override
            public void onFailure(ServerFailure error) {
                // TODO: do something...
            }
        });
    }

}
