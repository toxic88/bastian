package de.bastian.clan.client.place.user;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class UserPlace extends Place {

    private String token;
    private Long userId;

    public UserPlace(String token) {
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

    @Prefix("user")
    public static class Tokenizer implements PlaceTokenizer<UserPlace> {

        @Override
        public String getToken(UserPlace place) {
            return place.getToken();
        }

        @Override
        public UserPlace getPlace(String token) {
            return new UserPlace(token);
        }

    }

}
