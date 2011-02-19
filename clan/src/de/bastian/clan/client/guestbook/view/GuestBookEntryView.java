package de.bastian.clan.client.guestbook.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import de.bastian.clan.client.Clan;
import de.bastian.clan.shared.GuestBookEntry;
import de.bastian.clan.shared.GuestBookEntryProxy;

public class GuestBookEntryView extends Composite {

    private static GuestBookEntryViewUiBinder uiBinder = GWT.create(GuestBookEntryViewUiBinder.class);

    interface GuestBookEntryViewUiBinder extends UiBinder<Widget, GuestBookEntryView> {}

    public GuestBookEntryView(String name, String text) {
        initWidget(uiBinder.createAndBindUi(this));

        this.name.setInnerHTML(SafeHtmlUtils.fromString(name).asString());

        text = SafeHtmlUtils.fromString(text).asString();
        text = Clan.REGEXP.LINEBREAK.getRegExp().replace(text, Clan.REGEXP.LINEBREAK.getReplace());
        text = Clan.REGEXP.HYPERLINK.getRegExp().replace(text, Clan.REGEXP.HYPERLINK.getReplace());
        this.text.setInnerHTML(text);
    }

    public GuestBookEntryView(GuestBookEntry guestBookEntry) {
        this(guestBookEntry.getName(), guestBookEntry.getText());
    }

    public GuestBookEntryView(GuestBookEntryProxy guestBookEntry) {
        this(guestBookEntry.getName(), guestBookEntry.getText());
    }

    @UiField
    SpanElement name;

    @UiField
    SpanElement text;

}
