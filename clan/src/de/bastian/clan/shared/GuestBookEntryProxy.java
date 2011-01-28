package de.bastian.clan.shared;

import java.util.Date;

import com.google.gwt.requestfactory.shared.EntityProxy;
import com.google.gwt.requestfactory.shared.EntityProxyId;
import com.google.gwt.requestfactory.shared.ProxyForName;

@ProxyForName("de.bastian.clan.server.domain.GuestBookEntry")
public interface GuestBookEntryProxy extends EntityProxy {

    Long getId();
    String getName();
    String getText();
    Date getCreated();
    Date getChanged();
    Integer getVersion();

    void setName(String name);
    void setText(String text);

    @Override
    EntityProxyId<GuestBookEntryProxy> stableId();

}
