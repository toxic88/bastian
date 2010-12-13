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
        try {
            topicId = new Long(topicThemePost[0]);
        } catch (Exception e) {
            topicId = null;
        }

        if (topicThemePost.length >= 2) {
            try {
                themeId = new Long(topicThemePost[1]);
            } catch (Exception e) {
                themeId = null;
            }
        }

        if (topicThemePost.length == 3) {
            try {
                postId = new Long(topicThemePost[2]);
            } catch (Exception e) {
                postId = null;
            }
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

