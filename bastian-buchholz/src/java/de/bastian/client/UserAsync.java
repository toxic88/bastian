package de.bastian.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.List;

/**
 *
 * @author bastian
 */
public interface UserAsync {

    public void getList(AsyncCallback<List<String[]>> callback);

    public void createUser(String firstname, String lastname, AsyncCallback callback);

}
