package bastian.gwt.server.rpc;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import bastian.gwt.client.rpc.Echo;

public class EchoImpl extends RemoteServiceServlet implements Echo {

  public String echo(String s) {
    return "Server says: " + s;
  }

}
