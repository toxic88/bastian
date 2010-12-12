package de.bastian.clan.client.place.forum;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class EditPostPlace extends Place {

    private String token;
    private Long topicId;
    private Long themeId;
    private Long postId;

    public EditPostPlace(String token) {
        this.token = token;

        String[] topicThemePost = token.split(":");
        topicId = new Long(topicThemePost[0]);

        if (topicThemePost.length >= 2) {
            themeId = new Long(topicThemePost[1]);
        }

        if (topicThemePost.length == 3) {
            postId = new Long(topicThemePost[2]);
        } else {
            postId = null;
        }
    }

    public String getToken() {
        return token;
    }

    public Long getTopicId() {
        return topicId;
    }

    public Long getThemeId() {
        return themeId;
    }

    public Long getPostId() {
        return postId;
    }

    @Prefix("editPost")
    public static class Tokenizer implements PlaceTokenizer<EditPostPlace> {

        @Override
        public String getToken(EditPostPlace place) {
            return place.getToken();
        }

        @Override
        public EditPostPlace getPlace(String token) {
            return new EditPostPlace(token);
        }

    }

}

