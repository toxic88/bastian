package de.bastian.clan.client.place.picture;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class PicturesPlace extends Place {

    private String token;
    private int page;

    public PicturesPlace() {
        this("");
    }

    public PicturesPlace(String token) {
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

    @Prefix("pictures")
    public static class Tokenizer implements PlaceTokenizer<PicturesPlace> {

        @Override
        public String getToken(PicturesPlace place) {
            return place.getToken();
        }

        @Override
        public PicturesPlace getPlace(String token) {
            return new PicturesPlace(token);
        }

    }

}
