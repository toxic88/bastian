package de.bastian.clan.client.view.widgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.Constants;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.bastian.clan.client.Clan;

public class ConfirmPopupPanel extends PopupPanel {

    public static interface ConfirmPopupPanelConstants extends Constants {
        String yes();
        String no();
    }

    private static ClickHandler DEFAULT_NOHANDLER = new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
            ConfirmPopupPanel.get().hide();
        }
    };

    private static ConfirmPopupPanel instance;

    public static ConfirmPopupPanel get() {
        if (instance == null) {
            instance = new ConfirmPopupPanel();
        }

        return instance;
    }

    private HTML text = new HTML();

    private Button yes = new Button(Clan.MESSAGES.yes());

    private Button no = new Button(Clan.MESSAGES.no());

    private HandlerRegistration yesHandlerRegistration;

    private HandlerRegistration noHandlerRegistration;

    private ConfirmPopupPanel() {
        setAutoHideEnabled(false);
        setGlassEnabled(true);
        setNoHandler(null);

        HorizontalPanel h = new HorizontalPanel();
        h.add(yes);
        h.add(no);
        no.getElement().getParentElement().setClassName("right");

        VerticalPanel v = new VerticalPanel();
        v.add(text);
        text.setStyleName("text");
        v.add(h);

        setWidget(v);
    }

    public void setText(String text) {
        setHtml(SafeHtmlUtils.fromString(text));
    }

    public void setHtml(SafeHtml html) {
        text.setHTML(html);
    }

    public void setYesHandler(ClickHandler handler) {
        if (yesHandlerRegistration != null) {
            yesHandlerRegistration.removeHandler();
        }

        yesHandlerRegistration = yes.addClickHandler(handler);
    }

    public void setNoHandler(ClickHandler handler) {
        if (noHandlerRegistration != null) {
            noHandlerRegistration.removeHandler();
        }

        if (handler == null) {
            handler = DEFAULT_NOHANDLER;
        }

        noHandlerRegistration = no.addClickHandler(handler);
    }

}
