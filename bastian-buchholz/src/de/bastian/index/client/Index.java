package de.bastian.index.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

import de.bastian.index.client.data.Buzz;
import de.bastian.index.client.data.BuzzFeed;
import de.bastian.index.client.resources.Resources;

public class Index implements EntryPoint {

    public static Resources RESOURCES = GWT.create(Resources.class);

    @Override
    public void onModuleLoad() {
        RESOURCES.style().ensureInjected();

        AppView view = new AppView();
        RootPanel.get().add(view);

        final Element el = view.getContent();

        JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
        jsonp.requestObject("https://www.googleapis.com/buzz/v1/activities/110795566216949339348/@public?alt=json", new AsyncCallback<BuzzFeed>() {

            @Override
            public void onFailure(Throwable caught) {}

            @Override
            public void onSuccess(BuzzFeed result) {
                JsArray<Buzz> bs = result.getBuzzes();
                for (int i = 0; i < bs.length(); i++) {
                    Buzz b = bs.get(i);

                    String c = (b.getAttachments() != null && b.getAttachments().get(0).getContent() != null ? b.getAttachments().get(0).getContent() : b.getContent());

                    el.setInnerHTML(el.getInnerHTML() + "<div class='article'><a href='" + b.getAlternateLink().getUrl() + "'><h2>" + b.getTitle() + "</h2></a><p>" + c + "</p></div>");
                }
            }
        });

    }

}
