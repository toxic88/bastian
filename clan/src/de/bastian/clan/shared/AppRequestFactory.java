package de.bastian.clan.shared;

import com.google.gwt.requestfactory.shared.RequestFactory;

public interface AppRequestFactory extends RequestFactory {

    UserRequest userRequest();
    TopicRequest topicRequest();
    PostRequest postRequest();
    PictureRequest pictureRequest();
    PushClientRequest pushClientRequest();
    GuestBookEntryRequest guestBookEntryRequest();

}
