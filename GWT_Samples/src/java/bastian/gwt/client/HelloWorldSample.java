package bastian.gwt.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Panel;

public class HelloWorldSample extends SampleAbstract {

  final static Button b = new Button("Hello World", new ClickHandler() {

    public void onClick(ClickEvent event) {
      Window.alert("Hello World!");
    }

  });

  public static void run(Panel root) {
    SampleAbstract.run(root);

    root.add(b);
  }

}
