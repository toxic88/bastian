package de.bastian.clan.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class HelloPlace extends Place {

    @Prefix("home")
    public static class Tokenizer implements PlaceTokenizer<HelloPlace> {

        @Override
        public String getToken(HelloPlace place) {
            return "";
        }

        @Override
        public HelloPlace getPlace(String token) {
            return new HelloPlace();
        }

    }

}
