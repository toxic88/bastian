package de.bastian.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.List;

public interface UserManagerAsync {

    public void getList(AsyncCallback<List<String[]>> callback);

    public void createUser(String firstname, String lastname, String password, AsyncCallback callback);

    public void checkPassword(String password, AsyncCallback<Boolean> asyncCallback);

}
