package de.bastian.clan.shared;

import java.util.Date;

import com.google.gwt.requestfactory.shared.EntityProxy;
import com.google.gwt.requestfactory.shared.EntityProxyId;
import com.google.gwt.requestfactory.shared.ProxyFor;

@ProxyFor(de.bastian.clan.server.domain.User.class)
public interface UserProxy extends EntityProxy {

    public static enum Type
    {
        Admin, Member, User
    }

    Long getId();
    String getFirstname();
    String getLastname();
    String getEmail();
    String getPassword();
    Type getType();
    String getSteamid();
    Date getCreated();
    Date getLastLogin();

    void setFirstname(String firstname);
    void setLastname(String lastname);
    void setEmail(String email);
    void setPassword(String password);
    void setType(Type type);
    void setSteamid(String steamid);

    @Override
    EntityProxyId<UserProxy> stableId();

}
