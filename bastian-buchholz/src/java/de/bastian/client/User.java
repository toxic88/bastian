package de.bastian.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 *
 * @author bastian
 */
@RemoteServiceRelativePath("user")
public interface User extends RemoteService {
    public String myMethod();
}
