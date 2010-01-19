package bastian.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SampleEntryPoint implements EntryPoint {

  final DockLayoutPanel p = new DockLayoutPanel(Unit.EM);
  final FlowPanel root = new FlowPanel();
  final VerticalPanel n = new VerticalPanel();

  final Hyperlink helloWorldLink = new Hyperlink("Hello World", "HelloWorld");
  final Hyperlink helloWorldAsyncLink = new Hyperlink("Hello World Async", "HelloWorldAsync");
  final Hyperlink rpcLink = new Hyperlink("RPC Sample", "RPCSample");

  public void onModuleLoad() {
    renderLinks();

    renderLayout();

    initHistory();
  }

  private void renderLayout() {
    root.setStylePrimaryName("content");

    n.setSpacing(5);
    n.setStylePrimaryName("navigation");

    p.setStylePrimaryName("layout-panel");

    p.addNorth(new HTML("<h1 id='header'>GWT - Samples</h1>"), 4);
    p.addSouth(new HTML("<p id='footer'>Bastian Buchholz</p>"), 1);
    p.addWest(n, 15);
    p.add(root);

    RootLayoutPanel.get().add(p);
  }

  private void renderLinks() {
    n.add(helloWorldLink);
    n.add(helloWorldAsyncLink);
    n.add(rpcLink);
  }

  private void initHistory() {
    History.addValueChangeHandler(new ValueChangeHandler<String>() {

      private String lastToken = null;

      public void onValueChange(ValueChangeEvent<String> event) {
        String token = event.getValue();

        if (token.equals(lastToken)) {
          return;
        }

        if (token.equals("HelloWorld")) {
          HelloWorldSample.run(root);
        } else if (token.equals("HelloWorldAsync")) {
          HelloWorldAsyncSample.run(root);
        } else if (token.equals("RPCSample")) {
          RpcSample.run(root);
        }

        lastToken = token;
      }

    });

    History.fireCurrentHistoryState();
  }

}
