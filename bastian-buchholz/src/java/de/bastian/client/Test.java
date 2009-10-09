package de.bastian.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("test")
public interface Test extends RemoteService {
    public String myMethod(String s);
}
