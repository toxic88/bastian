package bastian.gwt.client;

import bastian.gwt.client.resources.Resources;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
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

  public static Resources Resources = GWT.create(Resources.class);

  final DockLayoutPanel p = new DockLayoutPanel(Unit.EM);
  final FlowPanel root = new FlowPanel();
  final VerticalPanel n = new VerticalPanel();

  final Hyperlink helloWorldLink = new Hyperlink("Hello World", "HelloWorld");
  final Hyperlink helloWorldAsyncLink = new Hyperlink("Hello World Async", "HelloWorldAsync");
  final Hyperlink rpcLink = new Hyperlink("RPC Sample", "RPC");
  final Hyperlink widgetLink = new Hyperlink("Widgets", "Widgets");
  final Hyperlink clientBundleLink = new Hyperlink("Client Bundle", "ClientBundle");

  public void onModuleLoad() {
    Resources.gwtSampleCss().ensureInjected();

    renderLinks();

    renderLayout();

    initHistory();
  }

  private void renderLayout() {
    root.setStylePrimaryName("content");
    root.add(new HTML("<--- Click on a link to see the sample"));

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
    n.add(widgetLink);
    n.add(clientBundleLink);
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
        } else if (token.equals("RPC")) {
          RpcSample.run(root);
        } else if (token.equals("Widgets")) {
          WidgetSample.run(root);
        } else if (token.equals("ClientBundle")) {
          ClientBundleSample.run(root);
        }

        lastToken = token;
      }

    });

    History.fireCurrentHistoryState();
  }

}
