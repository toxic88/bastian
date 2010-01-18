package bastian.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Hyperlink;

public class SampleEntryPoint implements EntryPoint {

  public void onModuleLoad() {

    final Hyperlink link = new Hyperlink("Hello World", "HelloWorld");

    History.addValueChangeHandler(new ValueChangeHandler<String>() {

      private String lastToken = null;

      public void onValueChange(ValueChangeEvent<String> event) {
        String token = event.getValue();

        if (token.equals(lastToken)) {
          return;
        }

        lastToken = token;
      }

    });

    RootPanel.get().add(link);
  }

}
