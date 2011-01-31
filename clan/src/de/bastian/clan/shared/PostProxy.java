package de.bastian.clan.shared;

import java.util.Date;

import com.google.gwt.requestfactory.shared.EntityProxy;
import com.google.gwt.requestfactory.shared.EntityProxyId;
import com.google.gwt.requestfactory.shared.ProxyForName;

@ProxyForName("de.bastian.clan.server.domain.Post")
public interface PostProxy extends EntityProxy {

    Long getId();
    String getTitle();
    String getText();
    Date getCreated();
    Date getChanged();
    Long getUser();
    Long getTheme();
    Long getTopic();
    Integer getVersion();

    void setTitle(String title);
    void setText(String text);
    void setTheme(Long theme);
    void setTopic(Long topic);

    @Override
    EntityProxyId<PostProxy> stableId();

}
