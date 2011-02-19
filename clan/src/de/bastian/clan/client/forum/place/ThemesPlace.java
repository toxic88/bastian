package de.bastian.clan.client.forum.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class ThemesPlace extends Place {

    private String token;
    private Long topicId;
    private int page;

    public ThemesPlace(String token) {
        this.token = token;

        String[] topicPage = token.split(":");
        try {
            topicId = new Long(topicPage[0]);
        } catch (Exception e) {
            topicId = null;
        }

        try {
            page = Integer.parseInt(topicPage[1]);
        } catch (Exception e) {
            page = 0;
        }
    }

    public String getToken() {
        return token;
    }

    public Long getTopicId() {
        return topicId;
    }

    public int getPage() {
        return page;
    }

    @Prefix("!themes")
    public static class Tokenizer implements PlaceTokenizer<ThemesPlace> {

        @Override
        public String getToken(ThemesPlace place) {
            return place.getToken();
        }

        @Override
        public ThemesPlace getPlace(String token) {
            return new ThemesPlace(token);
        }

    }

}
