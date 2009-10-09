package de.bastian.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import de.bastian.client.Test;

public class TestImpl extends RemoteServiceServlet implements Test {
    public String myMethod(String s) {
        // Do something interesting with 's' here on the server.
        return "Server says: " + s;
    }

}
