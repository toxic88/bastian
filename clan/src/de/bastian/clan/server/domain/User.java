package de.bastian.clan.server.domain;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.persistence.Version;
import javax.servlet.http.HttpServletRequest;

import com.google.gwt.requestfactory.server.RequestFactoryServlet;

import de.bastian.clan.server.ClanAuthFilter;
import de.bastian.clan.server.Util;
import de.bastian.clan.shared.UserProxy;
import de.bastian.clan.shared.UserProxy.Type;
import de.bastian.clan.shared.ValidationException;

@SuppressWarnings("serial")
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class User implements Serializable {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;

    @Persistent
    private String firstname;

    @Persistent
    private String lastname;

    @Persistent
    private String email;

    @Persistent
    private String password;

    @Persistent
    private Type type;

    @Persistent
    private String steamid;

    @Persistent
    private Date created;

    @Persistent
    private Date lastLogin;

    @Persistent
    @Version
    private Integer version;


    private static final PersistenceManager persistenceManager() {
        return PMF.get().getPersistenceManager();
    }

    private static final HttpServletRequest getServletRequest() {
        return RequestFactoryServlet.getThreadLocalRequest();
    }

    public static User findUser(Long id) {
        if (id == null) {
            return null;
        }

        PersistenceManager pm = persistenceManager();

        try {
            return pm.getObjectById(User.class, id);
        } catch(RuntimeException e) {
            return null;
        } finally {
            pm.close();
        }
    }

    @SuppressWarnings("unchecked")
    public static User findByEmail(String email) {
        if (email == null || email.equals("")) {
            return null;
        }

        PersistenceManager pm = persistenceManager();

        try {
            Query query = pm.newQuery(User.class, "email == :emailParam");

            List<User> results = (List<User>) query.execute(email);

            if (results.size() == 1) {
                return results.get(0);
            }
        } finally {
            pm.close();
        }

        return null;
    }

    public static List<User> findAll() {
        PersistenceManager pm = persistenceManager();

        try {
            List<User> list = new ArrayList<User>();

            Extent<User> extent = pm.getExtent(User.class, false);
            for (User u : extent) {
                list.add(u);
            }
            return list;
        } finally {
            pm.close();
        }
    }

    @SuppressWarnings("unchecked")
    public static User login(String email, String password) throws UserDoesNotExistException {
        PersistenceManager pm = persistenceManager();

        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            password = Util.bytesToHex(algorithm.digest(password.getBytes()));

            Query query = pm.newQuery(User.class, "email == :emailParam && password == :passwordParam");

            List<User> results = (List<User>) query.execute(email, password);

            if (results.size() == 1) {
                User user = pm.detachCopy(results.get(0));
                user.setLastLogin(new Date());
                user.persist();

                getServletRequest().getSession(true).setAttribute(ClanAuthFilter.APPSESSIONID, user);

                return user;
            }
        } catch (NoSuchAlgorithmException e) {

        } catch (UserAllreadyExistsException e) {

        } catch (ValidationException e) {

        } finally {
            pm.close();
        }

        throw new User().new UserDoesNotExistException();
    }

    public static void logout() {
        getServletRequest().getSession().removeAttribute(ClanAuthFilter.APPSESSIONID);
    }

    public static User isLoggedIn() {
        return (User) getServletRequest().getSession().getAttribute(ClanAuthFilter.APPSESSIONID);
    }

    public void persist() throws UserAllreadyExistsException, ValidationException {
        if (getFirstname() == null || getFirstname().trim().isEmpty() ||
                getLastname() == null || getLastname().trim().isEmpty() ||
                getPassword() == null || getPassword().trim().isEmpty() ||
                getEmail() == null || getEmail().trim().isEmpty()) {
            throw new ValidationException();
        }

        PersistenceManager pm = persistenceManager();

        try {
            if (getId() == null) { // only on create...
                if (User.findByEmail(getEmail()) != null) {
                    throw new UserAllreadyExistsException();
                }

                setCreated(new Date());
                setVersion(1);

                MessageDigest algorithm = MessageDigest.getInstance("MD5");
                setPassword(Util.bytesToHex(algorithm.digest(getPassword().getBytes())));
            } else {
                setVersion(getVersion() + 1);

                if (getPassword().length() != 32) { // TODO: change this stupid check
                    MessageDigest algorithm = MessageDigest.getInstance("MD5");
                    setPassword(Util.bytesToHex(algorithm.digest(getPassword().getBytes())));
                }
            }

            pm.makePersistent(this);
        } catch (NoSuchAlgorithmException e) {

        } finally {
            pm.close();
        }
    }

    public void remove() throws ValidationException {
        PersistenceManager pm = persistenceManager();

        try {
            if (User.isLoggedIn() == null || (getId() != User.isLoggedIn().getId() && User.isLoggedIn().getType().equals(UserProxy.Type.Admin))) {
                throw new ValidationException();
            }

            User user = pm.getObjectById(User.class, getId());
            pm.deletePersistent(user);
        } finally {
            pm.close();
        }
    }

    public class UserAllreadyExistsException extends Exception {}

    public class UserDoesNotExistException extends Exception {}


    public User() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getSteamid() {
        return steamid;
    }

    public void setSteamid(String steamid) {
        this.steamid = steamid;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

}
