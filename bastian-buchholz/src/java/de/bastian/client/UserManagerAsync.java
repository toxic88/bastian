package de.bastian.client;

import java.util.List;
import com.google.gwt.user.client.rpc.AsyncCallback;
import de.bastian.client.model.User;

public interface UserManagerAsync {

    public void getAll(AsyncCallback<List<User>> callback);

    public void createUser(String firstname, String password, AsyncCallback<Boolean> callback);

    public void login(String username, String password, AsyncCallback<Boolean> asyncCallback);

}
