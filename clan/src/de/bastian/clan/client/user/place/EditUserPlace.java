package de.bastian.clan.client.user.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class EditUserPlace extends Place {

    private String token;
    private Long userId;

    public EditUserPlace(String token) {
        this.token = token;

        try {
            userId = new Long(token);
        } catch (Exception e) {
            userId = null;
        }
    }

    public String getToken() {
        return token;
    }

    public Long getUserId() {
        return userId;
    }

    @Prefix("editUser")
    public static class Tokenizer implements PlaceTokenizer<EditUserPlace> {

        @Override
        public String getToken(EditUserPlace place) {
            return place.getToken();
        }

        @Override
        public EditUserPlace getPlace(String token) {
            return new EditUserPlace(token);
        }

    }

}