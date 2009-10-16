package de.bastian.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import java.util.List;

/**
 *
 * @author bastian
 */
@RemoteServiceRelativePath("user")
public interface User extends RemoteService {

    public void createUser(String firstname, String lastname);

    public List<String[]> getList();

}
