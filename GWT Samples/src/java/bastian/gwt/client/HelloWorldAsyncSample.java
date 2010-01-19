package bastian.gwt.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Panel;

public class HelloWorldAsyncSample extends SampleAbstract {

  final static Button b = new Button("Hello World Async", new ClickHandler() {

    public void onClick(ClickEvent event) {
      GWT.runAsync(new RunAsyncCallback() {

        public void onFailure(Throwable reason) {
          Window.alert("Failed!");
        }

        public void onSuccess() {
          Window.alert("Hello World Async!");
        }

      });
    }

  });

  public static void run(Panel root) {
    SampleAbstract.run(root);

    root.add(b);
  }

}
