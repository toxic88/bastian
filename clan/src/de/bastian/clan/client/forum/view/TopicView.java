package de.bastian.clan.client.forum.view;

import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.NoSelectionModel;

import de.bastian.clan.shared.TopicProxy;

public class TopicView extends Composite {

    private static TopicViewUiBinder uiBinder = GWT.create(TopicViewUiBinder.class);

    interface TopicViewUiBinder extends UiBinder<Widget, TopicView> {}

    interface Style extends CssResource {
        String topic();
    }

    private static class TopicCell extends AbstractCell<TopicProxy> {

        private String style;

        public void setStyle(String style) {
            this.style = style;
        }

        @Override
        public void render(Context context, TopicProxy topic, SafeHtmlBuilder sb) {
            if (topic == null) {
                return;
            }

            sb.appendHtmlConstant("<div class='" + style + "'>");
                sb.appendHtmlConstant("<a href='#themes:" + topic.getId() + "'>");
                    sb.appendEscaped(topic.getName());
                sb.appendHtmlConstant("</a>");
            sb.appendHtmlConstant("</div>");
        }

    }

    public TopicView() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiField
    Style style;

    @UiField
    CellList<TopicProxy> content;

    @UiFactory CellList<TopicProxy> getCellList() {
        final TopicCell tc = new TopicCell();

        CellList<TopicProxy> cl = new CellList<TopicProxy>(tc);

        cl.setSelectionModel(new NoSelectionModel<TopicProxy>());

        Scheduler.get().scheduleDeferred(new ScheduledCommand() {
            @Override
            public void execute() {
                tc.setStyle(style.topic());
            }
        });
        return cl;
    }

    public void setTopics(List<TopicProxy> topics) {
        content.setRowData(0, topics);
    }

}
