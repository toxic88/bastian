package de.bastian.clan.shared;

import java.util.Date;

import com.google.gwt.requestfactory.shared.EntityProxy;
import com.google.gwt.requestfactory.shared.EntityProxyId;
import com.google.gwt.requestfactory.shared.ProxyForName;

@ProxyForName("de.bastian.clan.server.domain.PushClient")
public interface PushClientProxy extends EntityProxy {

    Long getId();
    String getName();
    String getChannel();
    Date getCreated();
    Date getChanged();
    Integer getVersion();

    @Override
    EntityProxyId<PushClientProxy> stableId();

}
