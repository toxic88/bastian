package de.bastian.clan.client.place.picture;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class EditPicturePlace extends Place {

    private String token;
    private Long pictureId;

    public EditPicturePlace(String token) {
        this.token = token;

        try {
            pictureId = new Long(token);
        } catch (Exception e) {
            pictureId = null;
        }
    }

    public String getToken() {
        return token;
    }

    public Long getPictureId() {
        return pictureId;
    }

    @Prefix("editPicture")
    public static class Tokenizer implements PlaceTokenizer<EditPicturePlace> {

        @Override
        public String getToken(EditPicturePlace place) {
            return place.getToken();
        }

        @Override
        public EditPicturePlace getPlace(String token) {
            return new EditPicturePlace(token);
        }

    }

}
