package bastian.gwt.client.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("echo")
public interface Echo extends RemoteService {

  public String echo(String s);

}
