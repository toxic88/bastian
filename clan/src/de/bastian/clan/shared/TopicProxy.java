package de.bastian.clan.shared;

import java.util.Date;

import com.google.gwt.requestfactory.shared.EntityProxy;
import com.google.gwt.requestfactory.shared.EntityProxyId;
import com.google.gwt.requestfactory.shared.ProxyFor;

@ProxyFor(de.bastian.clan.server.domain.Topic.class)
public interface TopicProxy extends EntityProxy {

    Long getId();
    String getName();
    Date getCreated();
    Date getChanged();
    Long getUser();
    Integer getVersion();

    void setName(String name);
    void setUser(Long user);

    @Override
    EntityProxyId<TopicProxy> stableId();

}
