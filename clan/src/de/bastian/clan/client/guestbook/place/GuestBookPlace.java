package de.bastian.clan.client.guestbook.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class GuestBookPlace extends Place {

    private String token;
    private int page; // not implemented yet

    public GuestBookPlace() {
        this("");
    }

    public GuestBookPlace(String token) {
        this.token = token;

        try {
            page = Integer.parseInt(token);
        } catch (Exception e) {
            page = 0;
        }
    }

    public String getToken() {
        return token;
    }

    public int getPage() {
        return page;
    }

    @Prefix("guestbook")
    public static class Tokenizer implements PlaceTokenizer<GuestBookPlace> {

        @Override
        public String getToken(GuestBookPlace place) {
            return place.getToken();
        }

        @Override
        public GuestBookPlace getPlace(String token) {
            return new GuestBookPlace(token);
        }

    }

}
