/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bastian.gwt.client;

import bastian.gwt.client.rpc.Echo;
import bastian.gwt.client.rpc.EchoAsync;
import com.google.gwt.core.client.GWT;

import com.google.gwt.user.client.rpc.AsyncCallback;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

public class RpcSample extends SampleAbstract {

  final static AsyncCallback<String> callback = new AsyncCallback<String>() {

    public void onSuccess(String result) {
      lblServerReply.setText(result);
    }

    public void onFailure(Throwable caught) {
      lblServerReply.setText("Communication failed");
    }

  };

  final static EchoAsync getService() {
    return GWT.create(Echo.class);
  }

  final static Label lblServerReply = new Label();
  final static TextBox txtUserInput = new TextBox();
  final static Button btnSend = new Button("Send to server", new ClickHandler() {

    public void onClick(ClickEvent event) {
      getService().echo(txtUserInput.getText(), callback);
    }

  });

  public static void run(Panel root) {
    SampleAbstract.run(root);

    root.add(new Label("Input your text: "));
    root.add(txtUserInput);
    root.add(btnSend);
    root.add(lblServerReply);
  }

}
