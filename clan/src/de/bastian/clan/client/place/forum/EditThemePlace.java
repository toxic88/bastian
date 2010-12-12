package de.bastian.clan.client.place.forum;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class EditThemePlace extends Place {

    private String token;
    private Long topicId;
    private Long themeId;

    public EditThemePlace(String token) {
        this.token = token;

        String[] topicTheme = token.split(":");
        topicId = new Long(topicTheme[0]);

        if (topicTheme.length == 2) {
            themeId = new Long(topicTheme[1]);
        } else {
            themeId = null;
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

    @Prefix("editTheme")
    public static class Tokenizer implements PlaceTokenizer<EditThemePlace> {

        @Override
        public String getToken(EditThemePlace place) {
            return place.getToken();
        }

        @Override
        public EditThemePlace getPlace(String token) {
            return new EditThemePlace(token);
        }

    }

}
