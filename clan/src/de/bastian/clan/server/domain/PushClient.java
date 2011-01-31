package de.bastian.clan.server.domain;

import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import de.bastian.clan.server.PushServer;
import de.bastian.clan.server.ValidationException;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class PushClient {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;

    @Persistent
    private String name;

    @Persistent
    private String channel;

    @Persistent
    private Date created;

    @Persistent
    private Date changed;

    @Persistent
    private Integer version;

    // REQUEST METHODS //

    /**
     * Returns the PersistenceManager
     * @return PersistenceManager
     */
    public static final PersistenceManager persistenceManager() {
        return PMF.get().getPersistenceManager();
    }

    /**
     * Returns the PushClient with the corresponding id
     * @param id
     * @return PushClient
     */
    public static PushClient findPushClient(Long id) {
        if (id == null) {
            return null;
        }

        PersistenceManager pm = persistenceManager();

        try {
            return pm.getObjectById(PushClient.class, id);
        } catch(RuntimeException e) {
            return null;
        } finally {
            pm.close();
        }
    }

    /**
     * Returns all PushClients
     * @return List<PushClient>
     */
    @SuppressWarnings("unchecked")
    public static List<PushClient> findAll() {
        PersistenceManager pm = persistenceManager();

        try {
            Query query = pm.newQuery(PushClient.class);
            query.setOrdering("name ASC");

            List<PushClient> results = (List<PushClient>) query.execute();
            results.size(); // stupid check to receive all posts

            return results;
        } finally {
            pm.close();
        }
    }

    public static PushClient create(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }

        try {
            PushClient client = new PushClient(name);
            client.persist(); // just for creating an id

            client.setChannel(PushServer.createChannel(client));
            client.persist();

            return client;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Creates or updates a PushClient
     * @throws ValidationException
     */
    public void persist() throws ValidationException {
        if (getName() == null || getName().trim().isEmpty()) {
            throw new ValidationException();
        }

        PersistenceManager pm = persistenceManager();

        try {
            if (getId() == null) { // only on create...
                setCreated(new Date());
                setVersion(1);
            } else {
                setChanged(new Date());
                setVersion(getVersion() + 1);
            }

            pm.makePersistent(this);
        } finally {
            pm.close();
        }
    }

    /**
     * Removes the PushClient
     */
    public void remove() {
        PersistenceManager pm = persistenceManager();

        try {
            PushClient client = pm.getObjectById(PushClient.class, getId());
            pm.deletePersistent(client);
        } finally {
            pm.close();
        }
    }

    // CONSTRUCTOR //

    /**
     * Default constructor is required by the RequestFactory
     */
    public PushClient() {}

    public PushClient(String name) {
        setName(name);
    }

    // GETTER and SETTER //

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.trim();
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getChanged() {
        return changed;
    }

    public void setChanged(Date changed) {
        this.changed = changed;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

}
