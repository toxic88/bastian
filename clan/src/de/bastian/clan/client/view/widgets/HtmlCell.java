package de.bastian.clan.client.view.widgets;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public class HtmlCell extends AbstractCell<String> implements Cell<String> {

    @Override
    public void render(Context context, String value, SafeHtmlBuilder sb) {
        if (value != null) {
            sb.appendHtmlConstant(value);
        }
    }

}
