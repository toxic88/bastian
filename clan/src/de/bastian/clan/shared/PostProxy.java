package de.bastian.clan.shared;

import java.util.Date;

import com.google.gwt.requestfactory.shared.EntityProxy;
import com.google.gwt.requestfactory.shared.EntityProxyId;
import com.google.gwt.requestfactory.shared.ProxyFor;

@ProxyFor(de.bastian.clan.server.domain.Post.class)
public interface PostProxy extends EntityProxy {

    Long getId();
    String getTitle();
    String getText();
    Date getCreated();
    Date getChanged();
    Long getUser();
    Long getTheme();
    Long getTopic();

    void setTitle(String title);
    void setText(String text);
    void setUser(Long user);
    void setTheme(Long theme);
    void setTopic(Long topic);

    @Override
    EntityProxyId<PostProxy> stableId();

}
