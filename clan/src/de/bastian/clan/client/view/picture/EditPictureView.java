package de.bastian.clan.client.view.picture;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import de.bastian.clan.client.activity.picture.EditPictureActivity;
import de.bastian.clan.shared.PictureProxy;
import de.bastian.gwt.fileapi.client.file.FileReader;
import de.bastian.gwt.fileapi.client.file.event.LoadEndEvent;
import de.bastian.gwt.fileapi.client.file.exception.BrowserNotSupportedException;
import de.bastian.gwt.fileapi.client.ui.FileUpload;

public class EditPictureView extends Composite {

    public interface Presenter {
        void updatePicture(PictureProxy picture, String image, String description);
    }

    private static EditPictureViewUiBinder uiBinder = GWT.create(EditPictureViewUiBinder.class);

    interface EditPictureViewUiBinder extends UiBinder<Widget, EditPictureView> {}

    private EditPictureActivity activity;

    private PictureProxy picture;

    public EditPictureView() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiField
    ImageElement image;

    @UiField
    TextBox description;

    @UiField
    FileUpload upload;

    @UiField
    Button button;

    @UiHandler("upload")
    void onChangeUpload(ChangeEvent e) {
        if (upload.getFiles() == null || upload.getFiles().length() == 0) {
            return;
        }

        try {
            final FileReader fr = new FileReader();
            fr.addLoadEndHandler(new LoadEndEvent.Handler() {
                @Override
                public void onLoadEnd(LoadEndEvent e) {
                    if (fr.getError() == null) {
                        String res = (String) fr.getResult();
                        if (res.startsWith("data:image")) {
                            image.setSrc(res);
                        } else {
                            // TODO: do something...
                        }
                    }
                }
            });
            fr.readAsDataURL(upload.getFiles().get(0));
        } catch (BrowserNotSupportedException err) {
            // TODO: do something...
        }
    }

    @UiHandler("button")
    void onClickButton(ClickEvent e) {
        activity.updatePicture(picture, image.getSrc(), description.getText());
    }

    private void reset() {
        picture = null;
        image.setSrc("");
        description.setText("");
    }

    @Override
    protected void onDetach() {
        super.onDetach();
        reset();
    }

    public void setPicture(PictureProxy picture) {
        this.picture = picture;

        if (picture != null) {
            image.setSrc(picture.getImage());
            description.setText(picture.getDescription());
        }
    }

    public void setActivity(EditPictureActivity activity) {
        this.activity = activity;
    }

}
