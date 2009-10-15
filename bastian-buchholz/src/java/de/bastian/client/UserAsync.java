package de.bastian.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 *
 * @author bastian
 */
public interface UserAsync {
    public void myMethod(AsyncCallback<String> callback);
}
