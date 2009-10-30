package de.bastian.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.List;

public interface UserManagerAsync {

    public void getAll(AsyncCallback<List<String[]>> callback);

    public void createUser(String firstname, String password, AsyncCallback<Boolean> callback);

    public void login(String username, String password, AsyncCallback<Boolean> asyncCallback);

}
