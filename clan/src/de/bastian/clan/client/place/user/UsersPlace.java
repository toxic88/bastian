package de.bastian.clan.client.place.user;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class UsersPlace extends Place {

    @Prefix("users")
    public static class Tokenizer implements PlaceTokenizer<UsersPlace> {

        @Override
        public String getToken(UsersPlace place) {
            return "";
        }

        @Override
        public UsersPlace getPlace(String token) {
            return new UsersPlace();
        }

    }

}
