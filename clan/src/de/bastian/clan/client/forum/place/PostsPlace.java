package de.bastian.clan.client.forum.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class PostsPlace extends Place {

    private String token;
    private Long themeId;
    private int page;

    public PostsPlace(String token) {
        this.token = token;

        String[] themePost = token.split(":");
        try {
            themeId = new Long(themePost[0]);
        } catch (Exception e) {
            themeId = null;
        }

        try {
            page = Integer.parseInt(themePost[1]);
        } catch (Exception e) {
            page = 0;
        }
    }

    public String getToken() {
        return token;
    }

    public Long getThemeId() {
        return themeId;
    }

    public int getPage() {
        return page;
    }

    @Prefix("!posts")
    public static class Tokenizer implements PlaceTokenizer<PostsPlace> {

        @Override
        public String getToken(PostsPlace place) {
            return place.getToken();
        }

        @Override
        public PostsPlace getPlace(String token) {
            return new PostsPlace(token);
        }

    }

}
