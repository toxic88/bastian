package de.bastian.clan.client.mvp;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

import de.bastian.clan.client.place.HelloPlace;
import de.bastian.clan.client.place.LoginPlace;
import de.bastian.clan.client.place.forum.EditPostPlace;
import de.bastian.clan.client.place.forum.EditThemePlace;
import de.bastian.clan.client.place.forum.PostsPlace;
import de.bastian.clan.client.place.forum.ThemesPlace;
import de.bastian.clan.client.place.forum.TopicPlace;
import de.bastian.clan.client.place.picture.EditPicturePlace;
import de.bastian.clan.client.place.picture.PicturesPlace;
import de.bastian.clan.client.place.user.EditUserPlace;
import de.bastian.clan.client.place.user.UserPlace;
import de.bastian.clan.client.place.user.UsersPlace;

@WithTokenizers({
    HelloPlace.Tokenizer.class,
    LoginPlace.Tokenizer.class,

    UsersPlace.Tokenizer.class,
    EditUserPlace.Tokenizer.class,
    UserPlace.Tokenizer.class,

    TopicPlace.Tokenizer.class,
    ThemesPlace.Tokenizer.class,
    EditThemePlace.Tokenizer.class,
    PostsPlace.Tokenizer.class,
    EditPostPlace.Tokenizer.class,

    PicturesPlace.Tokenizer.class,
    EditPicturePlace.Tokenizer.class
})
public interface AppPlaceHistoryMapper extends PlaceHistoryMapper {}
