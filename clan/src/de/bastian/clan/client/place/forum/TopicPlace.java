package de.bastian.clan.client.place.forum;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class TopicPlace extends Place {

    @Prefix("forum")
    public static class Tokenizer implements PlaceTokenizer<TopicPlace> {

        @Override
        public String getToken(TopicPlace place) {
            return "";
        }

        @Override
        public TopicPlace getPlace(String token) {
            return new TopicPlace();
        }

    }

}
