package de.bastian.clan.client.activity.picture;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import de.bastian.clan.client.Clan;
import de.bastian.clan.client.ClientFactory;
import de.bastian.clan.client.mvp.AppActivity;
import de.bastian.clan.client.mvp.AppReceiver;
import de.bastian.clan.client.place.picture.EditPicturePlace;
import de.bastian.clan.client.view.picture.EditPictureView;
import de.bastian.clan.shared.PictureProxy;
import de.bastian.clan.shared.PictureRequest;
import de.bastian.gwt.fileapi.client.file.FileReader;

public class EditPictureActivity extends AppActivity {

    private Long pictureId;

    public EditPictureActivity(EditPicturePlace place, ClientFactory clientFactory) {
        super(place, clientFactory);
         this.pictureId = place.getPictureId();
    }

    @Override
    public void start(final AcceptsOneWidget containerWidget, EventBus eventBus) {
        final EditPictureView editPictureView = clientFactory.getEditPictureView();

        if (pictureId != null && FileReader.isSupported()) {
            PictureRequest request = Clan.REQUESTFACTORY.pictureRequest();
            request.findPicture(pictureId).fire(new AppReceiver<PictureProxy>() {
                @Override
                public void onSuccess(PictureProxy picture) {
                    if (Clan.CURRENTUSER != null) {
                        editPictureView.setPicture(picture);
                        containerWidget.setWidget(editPictureView.asWidget());
                    } else {
                        History.back();
                    }
                }
            });
        } else if (Clan.CURRENTUSER != null) {
            editPictureView.setPicture(null);
            containerWidget.setWidget(editPictureView.asWidget());
        } else {
            Scheduler.get().scheduleDeferred(new ScheduledCommand() {
                @Override
                public void execute() {
                    History.back();
                }
            });
        }
    }

}
