package de.bastian.clan.client.mvp;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

import de.bastian.clan.client.forum.place.EditPostPlace;
import de.bastian.clan.client.forum.place.EditThemePlace;
import de.bastian.clan.client.forum.place.PostsPlace;
import de.bastian.clan.client.forum.place.ThemesPlace;
import de.bastian.clan.client.forum.place.TopicPlace;
import de.bastian.clan.client.guestbook.place.GuestBookPlace;
import de.bastian.clan.client.picture.place.EditPicturePlace;
import de.bastian.clan.client.picture.place.PicturesPlace;
import de.bastian.clan.client.place.HelloPlace;
import de.bastian.clan.client.place.LoginPlace;
import de.bastian.clan.client.user.place.EditUserPlace;
import de.bastian.clan.client.user.place.UserPlace;
import de.bastian.clan.client.user.place.UsersPlace;

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
    EditPicturePlace.Tokenizer.class,

    GuestBookPlace.Tokenizer.class
})
public interface AppPlaceHistoryMapper extends PlaceHistoryMapper {}
