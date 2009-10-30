package de.bastian.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import java.util.List;

@RemoteServiceRelativePath("user")
public interface UserManager extends RemoteService {

    public boolean login(String username, String password);

    public boolean createUser(String firstname, String password);

    public List<String[]> getAll();

}
