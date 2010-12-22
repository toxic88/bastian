package de.bastian.clan.shared;

import java.util.Date;

import com.google.gwt.requestfactory.shared.EntityProxy;
import com.google.gwt.requestfactory.shared.EntityProxyId;
import com.google.gwt.requestfactory.shared.ProxyFor;

@ProxyFor(de.bastian.clan.server.domain.Picture.class)
public interface PictureProxy extends EntityProxy {

    Long getId();
    String getDescription();
    String getImage();
    Date getCreated();
    Date getChanged();
    Long getUser();

    void setDescription(String description);
    void setImage(String image);
    void setUser(Long user);

    @Override
    EntityProxyId<PostProxy> stableId();

}