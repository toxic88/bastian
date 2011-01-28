package de.bastian.clan.server.domain;

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

import com.google.appengine.api.datastore.Text;
import com.google.appengine.repackaged.org.json.JSONObject;

import de.bastian.clan.server.PushServer;
import de.bastian.clan.server.ValidationException;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class GuestBookEntry {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;

    @Persistent
    private String name;

    @Persistent
    private Text text;

    @Persistent
    private Date created;

    @Persistent
    private Date changed;

    @Persistent
    @Version
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
     * Returns the Post with the corresponding id
     * @param id
     * @return Post
     */
    public static GuestBookEntry findGuestBookEntry(Long id) {
        if (id == null) {
            return null;
        }

        PersistenceManager pm = persistenceManager();

        try {
            return pm.getObjectById(GuestBookEntry.class, id);
        } catch(RuntimeException e) {
            return null;
        } finally {
            pm.close();
        }
    }

    public static List<GuestBookEntry> findAll() {
        PersistenceManager pm = persistenceManager();

        List<GuestBookEntry> list = new ArrayList<GuestBookEntry>();

        try {
            Extent<GuestBookEntry> extent = pm.getExtent(GuestBookEntry.class, false);
            for (GuestBookEntry t : extent) {
                list.add(t);
            }
        } finally {
            pm.close();
        }

        return list;
    }

    @SuppressWarnings("unchecked")
    public static List<GuestBookEntry> findGuestBookEntrys(int start, int end) {
        PersistenceManager pm = persistenceManager();

        try {
            Query query = pm.newQuery(GuestBookEntry.class);
            query.setRange(start, end);
            query.setOrdering("created DESC");

            List<GuestBookEntry> results = (List<GuestBookEntry>) query.execute();
            results.size(); // stupid check to receive all posts

            return results;
        } finally {
            pm.close();
        }
    }

    /**
     * Creates or updates the Post
     * @throws ValidationException
     */
    public void persist() throws ValidationException {
        if (getName() == null || getName().trim().isEmpty() ||
                getText() == null || getText().trim().isEmpty()) {
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

            PushServer.sendMessageToAll(new JSONObject(this).toString());
        } finally {
            pm.close();
        }
    }

    /**
     * Removes the PushClient
     * @throws ValidationException
     */
    public void remove() {

        PersistenceManager pm = persistenceManager();

        try {
            GuestBookEntry guestBookEntry = pm.getObjectById(GuestBookEntry.class, getId());

            pm.deletePersistent(guestBookEntry);
        } finally {
            pm.close();
        }
    }

    // CONSTRUCTOR //

    /**
     * Default constructor is required by the RequestFactory
     */
    public GuestBookEntry() {}

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

    public String getText() {
        if (text == null) {
            return null;
        }
        return text.getValue();
    }

    public void setText(String text) {
        if (text == null) {
            this.text = null;
        } else {
            this.text = new Text(text.trim());
        }
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
