package de.bastian.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import java.util.List;

@RemoteServiceRelativePath("user")
public interface UserManager extends RemoteService {

    public boolean checkPassword(String password);

    public void createUser(String firstname, String lastname, String password);

    public List<String[]> getList();

}
