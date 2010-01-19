package bastian.gwt.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface EchoAsync {

  public void echo(String s, AsyncCallback<String> callback);

}
