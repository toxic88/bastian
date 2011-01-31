package de.bastian.clan.client.forum.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.Constants;
import com.google.gwt.requestfactory.client.RequestFactoryEditorDriver;
import com.google.gwt.requestfactory.shared.RequestContext;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import de.bastian.clan.client.Clan;
import de.bastian.clan.client.mvp.AppReceiver;
import de.bastian.clan.shared.PostProxy;
import de.bastian.clan.shared.PostRequest;
import de.bastian.clan.shared.TopicProxy;

public class EditThemeView extends Composite implements Editor<PostProxy> {

    private static EditThemeViewUiBinder uiBinder = GWT.create(EditThemeViewUiBinder.class);

    interface EditThemeViewUiBinder extends UiBinder<Widget, EditThemeView> {}

    interface Driver extends RequestFactoryEditorDriver<PostProxy, EditThemeView> {}

    private Driver driver = GWT.create(Driver.class);

    public interface EditThemeViewConstants extends Constants {
        String newTheme();
    }

    public EditThemeView() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiField
    HeadingElement header;

    @UiField
    TextBox title;

    @UiField
    TextArea text;

    @UiField
    Button save;

    @UiHandler("save")
    void onSaveClick(ClickEvent e) {
        RequestContext request = driver.flush();

        if (driver.hasErrors()) {
            // TODO: do something
        } else {
            request.fire(new AppReceiver<Void>() {
                @Override
                public void onSuccess(Void response) {
                    History.back();
                }
            });
        }
    }

    public void edit(TopicProxy topic, PostProxy theme) {
        driver.initialize(Clan.EVENTBUS, Clan.REQUESTFACTORY, this);

        if (theme == null) {
            header.setInnerHTML("<a href='#forum:'>" + Clan.MESSAGES.forum() + "</a> > <a href='#themes:" + topic.getId() + "'>" + topic.getName() + "</a> > " + Clan.MESSAGES.newTheme());
        } else {
            header.setInnerHTML("<a href='#forum:'>" + Clan.MESSAGES.forum() + "</a> > <a href='#themes:" + topic.getId() + "'>" + topic.getName() + "</a> > " + theme.getTitle());
        }

        PostRequest request = Clan.REQUESTFACTORY.postRequest();
        if (theme == null) {
            theme = request.create(PostProxy.class);
            theme.setTopic(topic.getId());
        }
        request.persist().using(theme);

        driver.edit(theme, request);
    }

}
