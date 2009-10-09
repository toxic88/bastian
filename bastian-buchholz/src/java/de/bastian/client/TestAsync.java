package de.bastian.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TestAsync {
    public void myMethod(String s, AsyncCallback<String> callback);
}
